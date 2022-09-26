package com.wellbeignatwork.backend.service.Collaboration;

import com.wellbeignatwork.backend.entity.Collaboration.QuestionCollaboration;
import com.wellbeignatwork.backend.entity.Collaboration.Quiz;
import com.wellbeignatwork.backend.repository.Collaboration.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    
	@Override
	public QuestionCollaboration addQuestion(QuestionCollaboration question) {
			return this.questionRepository.save(question);
	}

	@Override
	public QuestionCollaboration updateQuestion(QuestionCollaboration question) {
		return this.questionRepository.save(question);

	}

	@Override
	public Set<QuestionCollaboration> getQuestions() {
return new HashSet<>(this.questionRepository.findAll());
	}

	@Override
	public QuestionCollaboration getQuestion(Long questionId) {
		// TODO Auto-generated method stub
		return this.questionRepository.findById(questionId).get();
	}

	@Override
	public void deleteQuestion(Long questionId) {

		QuestionCollaboration question = new QuestionCollaboration();
        question.setQuesId(questionId);
        this.questionRepository.delete(question);
	}

	@Override
	public Set<QuestionCollaboration> getQuestionsOfQuiz(Quiz quiz) {
		return this.questionRepository.findByQuiz(quiz);
	}

	@Override
	public QuestionCollaboration get(Long questionsId) {
	       return this.questionRepository.getOne(questionsId);

	}

}
