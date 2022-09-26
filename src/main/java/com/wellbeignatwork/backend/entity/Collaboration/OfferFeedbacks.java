package com.wellbeignatwork.backend.entity.Collaboration;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class OfferFeedbacks extends FeedBackCollaboration {
    Long userId;
    String Name;
}
