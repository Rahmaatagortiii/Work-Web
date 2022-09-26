package com.wellbeignatwork.backend.repository.Collaboration;

import com.wellbeignatwork.backend.entity.Collaboration.Category;
import com.wellbeignatwork.backend.entity.Collaboration.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

	public List<Quiz> findBycategory(Category category);

	public List<Quiz> findByActive(boolean b);

	public List<Quiz> findByCategoryAndActive(Category category, boolean b);
	
	

}
