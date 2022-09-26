package com.wellbeignatwork.backend.entity.Evaluation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellbeignatwork.backend.entity.User.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sujet implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String nomSujet;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date dateAjout;
    private int nbYes;
    private int nbNo;
    private int nbpoint;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id")
    private User idUser;
    @OneToMany(mappedBy = "idSujet")
    @JsonIgnore
    public Set<VoteIdea> votesSujet;

}








