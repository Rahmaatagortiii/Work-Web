package com.wellbeignatwork.backend.service.Forum;

import com.wellbeignatwork.backend.entity.Forum.Comment;

import java.util.Collection;
import java.util.List;

public interface CommentService {
    public Comment createcomment(Comment comment,int idPost,Long idUser);
    public Collection<Comment> getAll();
    public Comment updatecomment(Comment comment);
    public void deletecomment(int id);
    public List<Integer> PostSatisfaction(int idPost);
    public List<Comment> sortByDate(int idPost);
}
