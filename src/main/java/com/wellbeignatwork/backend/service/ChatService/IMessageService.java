package com.wellbeignatwork.backend.service.ChatService;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.wellbeignatwork.backend.entity.Chat.Message;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IMessageService {
    public Message filterBadWords(Message message);
    public void saveDiscussion(List<Message> messages);
    public List<Message> retrieveDiscussions(@NotBlank Long roomId);
    public void getTopChattersGlobally() throws FirebaseMessagingException;
    public void archiveMessages() throws ExecutionException, InterruptedException, FirebaseMessagingException;
}
