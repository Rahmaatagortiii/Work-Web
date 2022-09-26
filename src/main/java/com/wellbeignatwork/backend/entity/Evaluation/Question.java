package com.wellbeignatwork.backend.entity.Evaluation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;



    @JsonIgnore
    @ManyToOne
    Survey survey;
    public Question(String description) {
        this.description = description;
    }




}
