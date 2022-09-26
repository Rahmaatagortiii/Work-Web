package com.wellbeignatwork.backend.service.ChatService;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.wellbeignatwork.backend.entity.Chat.Message;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.exceptions.chatExceptions.ResourceNotFoundException;
import com.wellbeignatwork.backend.repository.Chat.ChatRoomRepository;
import com.wellbeignatwork.backend.repository.Chat.MessageRepository;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import com.wellbeignatwork.backend.service.NotificationService.PushNotificationService;
import com.wellbeignatwork.backend.service.UserService.MailService;
import com.wellbeignatwork.backend.util.BadWordFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MessageService implements IMessageService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final PushNotificationService pushNotificationService;


    @Autowired
    public MessageService(MessageRepository messageRepository,
                          ChatRoomRepository chatRoomRepository,
                          UserRepository userRepository,
                          MailService mailService,
                          PushNotificationService pushNotificationService) {
        this.messageRepository = messageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.mailService=mailService;
        this.pushNotificationService=pushNotificationService;
    }

    //filter all bad words coming from messaged and replace them with *** (profanity)
    public Message filterBadWords(Message message) {
        String filteredContent = BadWordFilter.getCensoredText(message.getContent());
        message.setContent(filteredContent);
        return message;
    }


    public void saveDiscussion(List<Message> messages) {
        messageRepository.saveAll(messages);

    }


    public List<Message> retrieveDiscussions(@NotBlank Long roomId) {
        return chatRoomRepository.findById(roomId).map((messageRepository::getMessagesByChatroom))
                .orElseThrow(() -> new ResourceNotFoundException("chat room with id : " + roomId + "cannot be found verify your entry"));

    }


    @Scheduled(fixedRate = 30000)
    public void getTopChattersGlobally() throws FirebaseMessagingException {
        Map<String,Integer> data=new HashMap<>();
        StringBuilder res= new StringBuilder();
        Map<User, Integer> chattesMap = new HashMap<>();
        List<Message> messages = messageRepository.findAll();
        if (!messages.isEmpty()) {
            messages
                    .forEach(message -> {
                        //filling the map with users
                        chattesMap.put(message.getSender(), 0);
                    });
            //adding the repetition number to evry user
            for (Message message : messages) {
                if (chattesMap.containsKey(message.getSender()))
                    chattesMap.put(message.getSender(), chattesMap.get(message.getSender()) + 1);
            }

            //sort the map by value (using java8 lambda functions)
            Map<User, Integer> result = chattesMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            //result is heree  ..
            for (User user : result.keySet()) {
                log.info("user : + " + user.getDisplayName() + "has : " + result.get(user) + " messages");
                res.append("user : + ").append(user.getDisplayName()).append(" has : ").append(result.get(user)).append(" messages in our application \n");
                data.put(user.getDisplayName(),result.get(user));
            }

            //sendEmails
            mailService.sendMailToAllUsers("weekly top chatters",res.toString(),false);
            //sendNotifications
            pushNotificationService.sendPushNotificationToALlUsers("weekly top chatters",res.toString());
            //send data to firebase

           /* Firestore dbFirestore = FirestoreClient.getFirestore();
             dbFirestore.collection("top-chatters").document("top-chatters").set(data);
            */
        }


    }


    // delete all Messaged evry week At 00:00 on Sunday.

    @Scheduled(cron = "0 0 0 * * MON")
    public void archiveMessages() throws ExecutionException, InterruptedException, FirebaseMessagingException {
        Map<String, List<Message>> data = new HashMap<>();
        List<Message> cleanedList = new ArrayList<>();
        if (!messageRepository.findAll().isEmpty()) {
            messageRepository.findAll().forEach(message -> {
                message.getSender().setRooms(null);
                message.getSender().setRoles(null);
                message.getSender().setMessages(null);
                message.getChatroom().setUsers(null);
                message.getChatroom().setMessages(null);

                cleanedList.add(message);

            });
            //save old messages to firebase
            data.put("messages", cleanedList);
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> future = dbFirestore.collection("message-archive").document(new Date().toString()).set(data);
            //delete all messages from our dataBase
            log.info(future.get().getUpdateTime().toString());
            messageRepository.deleteAllInBatch();

            //sending mails
            mailService.sendMailToAllUsers("deleting all messages","all Messages are Cleaned from database and moved to our archives ",false);
            //sendNotification
            pushNotificationService.sendPushNotificationToALlUsers("all Messages are Cleaned from database and moved to our archives","weekly db cleaning");
            log.info("all Messages are Cleaned from database and moved to firebase cloud DB");
        }
    }


}
