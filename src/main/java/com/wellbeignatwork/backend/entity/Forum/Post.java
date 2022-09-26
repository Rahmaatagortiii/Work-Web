package com.wellbeignatwork.backend.entity.Forum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellbeignatwork.backend.entity.User.Tags;
import com.wellbeignatwork.backend.entity.User.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String subject;
    private String content;
    private LocalDateTime createdAt=LocalDateTime.now();
    private LocalDateTime modifiedAt;
    @ElementCollection(targetClass = Tags.class)
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tags")
    @Enumerated(EnumType.STRING)
    private Set<Tags> tags = new HashSet<>();
    String file;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "post_comment",cascade ={CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnore
    private List<Comment> comments;
    @OneToMany(mappedBy = "post",cascade ={CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnore
    private List<Reaction> reactions;
}
