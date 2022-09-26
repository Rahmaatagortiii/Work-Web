package com.wellbeignatwork.backend.payload;

import com.wellbeignatwork.backend.entity.Chat.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    List<Message>messages;
}
