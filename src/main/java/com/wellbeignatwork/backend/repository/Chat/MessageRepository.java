package com.wellbeignatwork.backend.repository.Chat;


import com.wellbeignatwork.backend.entity.Chat.ChatRoom;
import com.wellbeignatwork.backend.entity.Chat.Message;
import com.wellbeignatwork.backend.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> getMessagesByChatroom(ChatRoom chatRoom);
    List<Message>findMessagesByChatroomAndSender(ChatRoom chatroom, User sender);


}
