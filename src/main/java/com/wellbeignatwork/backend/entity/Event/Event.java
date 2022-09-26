package com.wellbeignatwork.backend.entity.Event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellbeignatwork.backend.entity.User.Tags;
import com.wellbeignatwork.backend.entity.User.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idEvent;
    private String eventName;
    private String eventSeason;
    private String eventLocalisation;
    private int nbrMaxParticipant;
    private double frais;
    private double revenue;
    String image;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ElementCollection
    @CollectionTable(name = "eventTags",joinColumns = @JoinColumn(name = "idEvent"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Tags> eventTags;
    @ManyToMany(fetch = FetchType.LAZY ,mappedBy ="events")
    @JsonIgnore
    private Set<User> users;
    @JsonIgnore

    @OneToMany (mappedBy = "event",cascade = CascadeType.ALL)
    private List<FeedBack> feedBacks;




}
