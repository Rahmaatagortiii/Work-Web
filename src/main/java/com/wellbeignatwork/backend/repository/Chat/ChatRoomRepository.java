package com.wellbeignatwork.backend.repository.Chat;


import com.wellbeignatwork.backend.entity.Chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    ChatRoom findByUniqueKey(String uniqueKey);
    List<ChatRoom> findByRoomNameIsNull();

}
