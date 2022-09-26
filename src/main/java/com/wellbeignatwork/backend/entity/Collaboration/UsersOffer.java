package com.wellbeignatwork.backend.entity.Collaboration;

import com.wellbeignatwork.backend.entity.User.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
public class UsersOffer implements Serializable {
    @EmbeddedId
    UsersOfferKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("offerId")
    @JoinColumn(name = "offer_id")
    Offer offer;

    Boolean isAccepted = false;
    Boolean isFavorite = false;
    Boolean isInvited = false;

    Float rate;
    String feedback;

}
