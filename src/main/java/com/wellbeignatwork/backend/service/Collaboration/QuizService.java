package com.wellbeignatwork.backend.service.Collaboration;


import com.wellbeignatwork.backend.entity.Collaboration.Category;
import com.wellbeignatwork.backend.entity.Collaboration.Quiz;

import java.util.List;
import java.util.Set;

public interface QuizService {

	public Quiz addQuiz(Quiz quiz);
	
	public Quiz updateQuiz(Quiz quiz);
	
	public void deleteQuiz(Long quizId);
	
	public Quiz getQuiz(Long quizId);
	
	public Set<Quiz> getQuizzes();
	
	public List<Quiz> getQuizzesOfCategory(Category category);
	
	public List<Quiz> getActiveQuizzes();
	
	public List<Quiz> getActiveQuizzesOfCategory(Category category);
	
}
