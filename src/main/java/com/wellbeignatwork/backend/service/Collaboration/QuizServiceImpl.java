package com.wellbeignatwork.backend.service.Collaboration;

import com.wellbeignatwork.backend.entity.Collaboration.Category;
import com.wellbeignatwork.backend.entity.Collaboration.Quiz;
import com.wellbeignatwork.backend.repository.Collaboration.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

	@Override
	public Quiz addQuiz(Quiz quiz) {
		   return this.quizRepository.save(quiz);
    }

	@Override
	public Quiz updateQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
	}

	@Override
	public void deleteQuiz(Long quizId) {
		this.quizRepository.deleteById(quizId);
	    	
	}

	@Override
	public Quiz getQuiz(Long quizId) {
	     return this.quizRepository.findById(quizId).get();
	     }

	@Override
	public Set<Quiz> getQuizzes() {
	return new HashSet<Quiz>(this.quizRepository.findAll());
	}

	@Override
	public List<Quiz> getQuizzesOfCategory(Category category) {
		return this.quizRepository.findBycategory(category);
	}

	@Override
	public List<Quiz> getActiveQuizzes() {
		   return this.quizRepository.findByActive(true);
		   }

	@Override
	public List<Quiz> getActiveQuizzesOfCategory(Category category) {
	      return this.quizRepository.findByCategoryAndActive(category, true);
	}

}
