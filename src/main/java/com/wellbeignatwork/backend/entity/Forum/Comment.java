package com.wellbeignatwork.backend.entity.Forum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idComment;
    private String commentContent;
    private LocalDateTime createDate=LocalDateTime.now();
    private LocalDateTime modifyDate;
    private Long idUser;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    private Post post_comment;
}
