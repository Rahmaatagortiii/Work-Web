package com.wellbeignatwork.backend.repository.Forum;

import com.wellbeignatwork.backend.entity.Forum.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface PostRepository extends CrudRepository<Post,Integer> {
    @Query("from Post p order by p.createdAt desc")
    Page<Post> findAll( Pageable pageable);
    Post findPostBySubject(String subject);
}
