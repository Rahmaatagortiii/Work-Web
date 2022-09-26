package com.wellbeignatwork.backend.repository.Collaboration;

import com.wellbeignatwork.backend.entity.Collaboration.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {
    Boolean existsByName(String name);
    void deleteByName(String name);
}
