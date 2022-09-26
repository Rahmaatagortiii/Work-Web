package com.wellbeignatwork.backend.service.NotificationService;



import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;


import com.wellbeignatwork.backend.payload.PushNotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
@Slf4j
@Service
public class FCMService {



    public void sendMessageToToken(PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        log.info(" sendMessageToToken invoked");
        Message message = getPreconfiguredMessageToToken(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
        log.info("response : "+response);
        log.info("Sent message to token. Device token: " + request.getToken() + ", " + response+ " msg "+jsonOutput);
    }

    public void sendMessageToTokenWithExtraData(PushNotificationRequest request) throws ExecutionException, InterruptedException {
        Message message =getPreconfiguredMessageWithData(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
        log.info("response : "+response);
        log.info("Sent message to token. Device token: " + request.getToken() + ", " + response+ " msg "+jsonOutput);
    }

    public void sendMessageToTopic(PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageWithoutData(request);
        String response = sendAndGetResponse(message);
        log.info("Sent message to Topic: " + request.getTopic() + ", " + response);
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }


    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setTag(topic).build()).build();
    }
    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }
    private Message getPreconfiguredMessageToToken(PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).setToken(request.getToken())
                .build();
    }
    private Message getPreconfiguredMessageWithoutData(PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).setTopic(request.getTopic())
                .build();
    }
    private Message getPreconfiguredMessageWithData(PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).putAllData(request.getData()).setToken(request.getToken())
                .build();
    }
    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return Message.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
                        new Notification(request.getTitle(), request.getMessage()));
    }


}
