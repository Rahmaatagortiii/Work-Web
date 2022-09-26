package com.wellbeignatwork.backend.repository.Collaboration;


import com.wellbeignatwork.backend.entity.Collaboration.Collaboration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ICollaboration extends JpaRepository<Collaboration, Long> {

}
