package com.wellbeignatwork.backend.service.Collaboration;

import com.wellbeignatwork.backend.entity.Collaboration.QuestionCollaboration;
import com.wellbeignatwork.backend.entity.Collaboration.Quiz;

import java.util.Set;

public interface QuestionService {

	public QuestionCollaboration addQuestion(QuestionCollaboration question);

	public QuestionCollaboration updateQuestion(QuestionCollaboration question);

	public Set<QuestionCollaboration> getQuestions();

	public QuestionCollaboration getQuestion(Long questionId);

    public void deleteQuestion(Long quesId);

	public Set<QuestionCollaboration> getQuestionsOfQuiz(Quiz quiz);

	public QuestionCollaboration get(Long questionId);
}
