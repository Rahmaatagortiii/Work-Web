package com.wellbeignatwork.backend.repository.Collaboration;

import com.wellbeignatwork.backend.entity.Collaboration.QuestionCollaboration;
import com.wellbeignatwork.backend.entity.Collaboration.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QuestionRepository extends JpaRepository<QuestionCollaboration, Long> {

	public Set<QuestionCollaboration> findByQuiz(Quiz quiz);

}
