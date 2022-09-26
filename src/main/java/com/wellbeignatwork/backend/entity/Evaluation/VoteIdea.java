package com.wellbeignatwork.backend.entity.Evaluation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellbeignatwork.backend.entity.User.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VoteIdea implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVote;
    private  int nbYes;
    private  int nbNo;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id")
    @JsonIgnore
    private User idUser;


    @ManyToOne
    @JoinColumn(name = "idSujet", referencedColumnName = "id")

    @JsonIgnore
    Sujet idSujet;




}
