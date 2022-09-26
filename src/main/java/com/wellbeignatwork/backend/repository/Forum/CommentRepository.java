package com.wellbeignatwork.backend.repository.Forum;

import com.wellbeignatwork.backend.entity.Forum.Comment;
import com.wellbeignatwork.backend.entity.Forum.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment,Integer> {
    @Query("SELECT count(c) FROM Comment c where c.idUser=:idUser")
    public int NbrCommentByUser(Long idUser);
    @Query("SELECT count(c) FROM Comment c where c.post_comment=:post")
    public int NbrCommentByPost(Post post);
    @Query("SELECT c FROM Comment c where c.post_comment=:post order by c.createDate desc")
    public List<Comment> sortbydate(Post post);
}
