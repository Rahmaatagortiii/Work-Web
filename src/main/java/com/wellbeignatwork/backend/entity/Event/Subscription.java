package com.wellbeignatwork.backend.entity.Event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellbeignatwork.backend.entity.User.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Long idSubscription;
    private String eventName;
    private double subscriptionFee;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;
    @JsonIgnore

    @ManyToOne
    private User user;



}
