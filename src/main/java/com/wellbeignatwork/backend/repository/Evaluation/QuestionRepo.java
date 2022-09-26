package com.wellbeignatwork.backend.repository.Evaluation;

import com.wellbeignatwork.backend.entity.Evaluation.Question;
import com.wellbeignatwork.backend.entity.Evaluation.Survey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepo extends CrudRepository<Question,Integer> {
    public List<Question> findQuestionsBySurvey(Survey survey);
}
