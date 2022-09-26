package com.wellbeignatwork.backend.service.Evaluation;




import com.wellbeignatwork.backend.entity.Evaluation.Answer;
import com.wellbeignatwork.backend.entity.Evaluation.Question;
import com.wellbeignatwork.backend.entity.User.User;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface IntQVTService {
     User addUser(User u);
     String UserAnswer(List<Answer> answer);
     String nbreSentiment();
     ByteArrayInputStream load();
     List<Question> SendSurvey();


















}
