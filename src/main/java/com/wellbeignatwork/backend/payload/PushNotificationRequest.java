package com.wellbeignatwork.backend.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationRequest {
    private String title;
    private String message;
    private String topic;
    private String token;
    private Map<String,String>data;


    public PushNotificationRequest(String title,String message,String topic) {
        this.title=title;
        this.message=message;
        this.topic=topic;
    }

    public PushNotificationRequest(String title,String message,String token,Map<String,String>data) {
        this.title=title;
        this.message=message;
        this.token=token;
        this.data=data;
    }


    public PushNotificationRequest(String title,String message) {
        this.title=title;
        this.message=message;

    }



}
