package com.wellbeignatwork.backend.controller.Chat;


import com.google.cloud.storage.StorageOptions;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.wellbeignatwork.backend.entity.Chat.ChatRoom;
import com.wellbeignatwork.backend.entity.Chat.Message;
import com.wellbeignatwork.backend.payload.MessageRequest;
import com.wellbeignatwork.backend.payload.MessageResponse;
import com.wellbeignatwork.backend.service.ChatService.ChatRoomService;
import com.wellbeignatwork.backend.service.ChatService.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@CrossOrigin("*")
@RestController

public class ChatController {
    private final ChatRoomService chatRoomService;

    private final MessageService messageService;

    @Autowired
    private ChatController(
            ChatRoomService chatRoomService
            , MessageService messageService
    ) {
        this.chatRoomService = chatRoomService;

        this.messageService = messageService;
    }




    @PostMapping("/chatroom/add-room")
    @ResponseBody
    public ChatRoom createChatRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.createChatRoom(chatRoom);
    }

    @DeleteMapping("/chatroom/delete-room")
    @ResponseBody
    public void deleteChatRoom(@RequestBody ChatRoom chatRoom) {
        chatRoomService.deleteChatRoom(chatRoom);
    }

    @PutMapping("/chatroom/update-room")
    @ResponseBody
    public ChatRoom updateRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.updateChatRoom(chatRoom);
    }

    @GetMapping("/chatroom/all-rooms")
    @ResponseBody
    @CrossOrigin("*")
    public List<ChatRoom> getAllRooms() {
        return chatRoomService.getAllRooms();
    }

    @GetMapping("/chatroom/all-public-rooms")
    public List<ChatRoom> getPublicRooms() {
        return chatRoomService.getPublicRooms();
    }


    @GetMapping("/chatroom/addUserToRoom/{chatRoomId}/{userId}")
    @ResponseBody
    public void addUserToChatRoom(@NotBlank @Valid @PathVariable Long chatRoomId, @NotBlank @Valid @PathVariable Long userId) {
        chatRoomService.addUserToChatRoom(chatRoomId, userId);
    }
    //http://localhost:8081/Wellbeignatwork/chatroom/save-discussion"
    @GetMapping("/chatroom/removeUserFromRoom/{chatRoomId}/{userId}")
    @ResponseBody
    public void removeUserFromRoom(@NotBlank @Valid @PathVariable Long chatRoomId, @NotBlank @Valid @PathVariable Long userId) {
        chatRoomService.removeUserFromChatRoom(chatRoomId, userId);
    }

    @GetMapping("/chatroom/findChatroomByUsersAndUniqueKey/{user1ID}/{user2ID}")
    @ResponseBody
    public ChatRoom findChatroomByUsersAndUniqueKey(@PathVariable Long user1ID,@PathVariable Long user2ID){
        return chatRoomService.findRoomByUsersAndUniqueKey(user1ID,user2ID);
    }


    @MessageMapping("/chat/{roomId}/{senderID}")
    public void roomBasedChat(@Payload Message message, @Valid @DestinationVariable Long roomId, @Valid @DestinationVariable Long senderID) throws MessagingException, FirebaseMessagingException {

        chatRoomService.roomBasedChat(messageService.filterBadWords(message), roomId, senderID);

    }



    @MessageMapping("/chat/oneToOne/{senderId}/{recieverId}/{roomUniqueKey}")
    public void oneToOneChat(@Payload Message message, @Valid @DestinationVariable Long senderId
            , @Valid @DestinationVariable Long recieverId
            ,@DestinationVariable String roomUniqueKey) throws MessagingException, FirebaseMessagingException {

        chatRoomService.oneToOneChat(messageService.filterBadWords(message), senderId, recieverId,roomUniqueKey);

    }

    @MessageMapping("/chat/public")
    public void publicCHat(@Payload Message message) throws FirebaseMessagingException {
        chatRoomService.publicChat(messageService.filterBadWords(message));
    }

    //http://localhost:8081/Wellbeignatwork/chatroom/save-discussion"
    @PostMapping("/chatroom/save-discussion")
    @ResponseBody
    public ResponseEntity<?> saveDiscussion(@Valid @RequestBody MessageRequest request) {
        messageService.saveDiscussion(request.getMessages());
        return new ResponseEntity<>(new MessageResponse(HttpStatus.OK.value(), "Discussion Saved Successfully"), HttpStatus.OK);
    }
    //http://localhost:8081/Wellbeignatwork/chatroom/retrieve-discussion/{roomId}"
    @GetMapping("/chatroom/retrieve-discussion/{roomId}")
    @ResponseBody
    public ResponseEntity<?> retrieveDiscussions(@PathVariable Long roomId) {
        return new ResponseEntity<>(messageService.retrieveDiscussions(roomId), HttpStatus.OK);
    }
    //http://localhost:8081/Wellbeignatwork/chatrooms/most-active-rooms"
    @GetMapping("/chatrooms/most-active-rooms")
    @ResponseBody
    public ResponseEntity<?> mostActiveRooms() {
        return new ResponseEntity<>(chatRoomService.getMostActiveChatRooms(), HttpStatus.OK);
    }


 //http://localhost:8081/Wellbeignatwork/chatroom/inviteUserToRoom/{roomId}/{userId}

    @GetMapping("/chatroom/inviteUserToRoom/{roomId}/{userId}")
    public void inviteUserToRoom(@PathVariable Long roomId,@PathVariable Long userId){
        chatRoomService.inviteUserToChatRoom(userId,roomId);
    }

    //http://localhost:8081/Wellbeignatwork/chatroom/acceptInvitation/1/2
    @GetMapping("/chatroom/acceptInvitation/{roomId}/{userId}")
    public void acceptInvitation(@PathVariable Long roomId,@PathVariable Long userId){
        chatRoomService.acceptInvitation(userId,roomId);
    }
    //http://localhost:8081/Wellbeignatwork/chatroom/bannUserFromRoom/{userId}/{roomId}"
    @GetMapping("/chatroom/bannUserFromRoom/{userId}/{roomId}")
    public void bannUserFromRoom(@PathVariable Long userId,@PathVariable Long roomId){
        chatRoomService.bannUserFromChatRoom(userId,roomId);
    }

    @GetMapping("/chatroom/unbannUserFromRoom/{userId}/{roomId}")
    public void unbannUserFromRoom(@PathVariable Long userId,@PathVariable Long roomId){
        chatRoomService.unbannUserFromChatRoom(userId,roomId);
    }
}
