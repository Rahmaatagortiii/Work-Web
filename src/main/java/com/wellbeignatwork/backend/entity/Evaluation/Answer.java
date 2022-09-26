package com.wellbeignatwork.backend.entity.Evaluation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Answer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAnswer;

    @Temporal(TemporalType.DATE)
    private Date CreatedAt;
    private String content;
    private Integer questionId;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Sentiment Sentiment;

}
