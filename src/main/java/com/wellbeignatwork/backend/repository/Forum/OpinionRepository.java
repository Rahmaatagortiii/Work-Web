package com.wellbeignatwork.backend.repository.Forum;

import com.wellbeignatwork.backend.entity.Forum.Opinion;
import com.wellbeignatwork.backend.entity.User.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpinionRepository extends CrudRepository<Opinion,Long> {
    List<Opinion> findAllByUser(User user);
}
