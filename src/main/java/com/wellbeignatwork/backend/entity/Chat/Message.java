package com.wellbeignatwork.backend.entity.Chat;

import com.wellbeignatwork.backend.entity.User.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendAt = new Date();

    @ManyToOne
    private User sender;

    @ManyToOne
    private ChatRoom chatroom;

}
