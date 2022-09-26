package com.wellbeignatwork.backend.service.ChatService;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.sun.istack.NotNull;
import com.wellbeignatwork.backend.entity.Chat.ChatRoom;
import com.wellbeignatwork.backend.entity.Chat.Message;
import com.wellbeignatwork.backend.entity.User.User;
import org.springframework.messaging.MessagingException;

import java.util.List;

public interface IChatService {

    public ChatRoom createChatRoom(ChatRoom chatRoom);
    public void deleteChatRoom(ChatRoom chatRoom);
    public ChatRoom updateChatRoom(ChatRoom chatRoom);
    public List<ChatRoom> getAllRooms();
    public List<ChatRoom> getPublicRooms();
    public List<ChatRoom> getMostActiveChatRooms();
    public void calculateResponseRatePerRoom();
    public void addUserToChatRoom(@NotNull Long chatRoomId, @NotNull Long userId);
    public void removeUserFromChatRoom(Long chatRoomId, Long userId);
    public void oneToOneChat(Message message, Long senderId, Long recieverId,String roomUniqueKey) throws FirebaseMessagingException;
    public void roomBasedChat(Message message, Long roomId, Long senderId) throws MessagingException, FirebaseMessagingException;
    public void publicChat(Message message) throws FirebaseMessagingException;
    public void inviteUserToChatRoom(Long userId,Long roomId);
    public void acceptInvitation(Long userID,Long roomId);
    public void bannUserFromChatRoom(Long userId,Long roomId);
    public void autoBannUsersFromChatRooms(int BadWordsFound, User user, ChatRoom room);
    public void unbannUserFromChatRoom(Long userId,Long roomId);

}