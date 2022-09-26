package com.wellbeignatwork.backend.service.NotificationService;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.wellbeignatwork.backend.payload.PushNotificationRequest;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface INotificationService {

     void sendPushNotificationToToken(PushNotificationRequest request);
     void sendToTopic(PushNotificationRequest request);
     void subScribeUsersToTopic(List<String> tokens, String topic) throws FirebaseMessagingException;
     void sendPushNotificationToALlUsers(String message,String title) throws FirebaseMessagingException;
     void chatReminderForInavtiveUsers() throws FirebaseMessagingException;
     void sendMessageToTokenWithExtraData(PushNotificationRequest request) throws ExecutionException, InterruptedException;
}
