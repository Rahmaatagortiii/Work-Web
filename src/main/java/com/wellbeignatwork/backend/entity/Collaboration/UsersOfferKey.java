package com.wellbeignatwork.backend.entity.Collaboration;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UsersOfferKey implements Serializable {

    @Column(name = "user_id")
    Long userId;
    @Column(name = "offer_id")
    Long offerId;

}
