package com.wellbeignatwork.backend.repository.Collaboration;

import com.wellbeignatwork.backend.entity.Collaboration.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
