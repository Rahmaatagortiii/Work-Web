package com.wellbeignatwork.backend.entity.Collaboration;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class FeedBackCollaboration {
    @NotNull(message = "Rating is required")
    @Min(value = 0, message = "Min Rating 0")
    @Max(value = 5, message = "Max Rating 5")
    Float rate;
    @NotEmpty(message = "Feedback must be not empty")
    String content;
}
