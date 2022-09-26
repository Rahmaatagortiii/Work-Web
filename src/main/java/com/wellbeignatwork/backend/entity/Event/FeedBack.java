package com.wellbeignatwork.backend.entity.Event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class FeedBack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idFeedBack;
    @NotNull(message = "Rating is required")
    @Min(value = 0, message = "Min Rating 0")
    @Max(value = 5, message = "Max Rating 5")
    private Float rate;
    @NotEmpty(message = "Feedback must be not empty")
    private String content;
    private Long idUser;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Event event;
}
