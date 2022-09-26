package com.wellbeignatwork.backend.controller.Collaboration;

import com.wellbeignatwork.backend.entity.Collaboration.QuestionCollaboration;
import com.wellbeignatwork.backend.entity.Collaboration.Quiz;
import com.wellbeignatwork.backend.service.Collaboration.QuestionService;
import com.wellbeignatwork.backend.service.Collaboration.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")

public class QuestionController {

    @Autowired
    private QuestionService service;

    @Autowired
    private QuizService quizService;

    //http://localhost:8081/Wellbeignatwork/question/
    @PostMapping("/")
    public ResponseEntity<QuestionCollaboration> add(@RequestBody QuestionCollaboration question) {
        return ResponseEntity.ok(this.service.addQuestion(question));
    }

    //http://localhost:8081/Wellbeignatwork/question/
    @PutMapping("/")
    public ResponseEntity<QuestionCollaboration> update(@RequestBody QuestionCollaboration question) {
        return ResponseEntity.ok(this.service.updateQuestion(question));
    }

    //http://localhost:8081/Wellbeignatwork/question/quiz/8
    @GetMapping("/quiz/{qid}")
    public ResponseEntity<?> getQuestionOfQuiz(@PathVariable("qid") Long qid){
    	Quiz quiz = this.quizService.getQuiz(qid);
    	Set<QuestionCollaboration> questionsOfQuiz = quiz.getQuestions();
    	List list = new ArrayList<>(questionsOfQuiz);
    	
    	if(list.size() > Integer.parseInt(quiz.getNumberOfQuestions()) ) {
    	list.subList(0,Integer.parseInt(quiz.getNumberOfQuestions()));	
    	}//if
    	Collections.shuffle(list);
    	return ResponseEntity.ok(list);
    }

    //http://localhost:8081/Wellbeignatwork/question/quiz/all/8
    @GetMapping("/quiz/all/{qid}")
    public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid) {
        Quiz quiz = new Quiz();
        quiz.setqId(qid);
        Set<QuestionCollaboration> questionsOfQuiz = this.service.getQuestionsOfQuiz(quiz);
        return ResponseEntity.ok(questionsOfQuiz);

//        return ResponseEntity.ok(list);


    }

    //get single question
   // http://localhost:8081/Wellbeignatwork/question/5
    @GetMapping("/{quesId}")
    public QuestionCollaboration get(@PathVariable("quesId") Long quesId) {
        return this.service.getQuestion(quesId);
    }

    //delete question
    //http://localhost:8081/Wellbeignatwork/question/5
    @DeleteMapping("/{quesId}")
    public void delete(@PathVariable("quesId") Long quesId) {
        this.service.deleteQuestion(quesId);
    }

    //http://localhost:8081/Wellbeignatwork/question/eval-quiz
    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<QuestionCollaboration> questions) {
        System.out.println(questions);
        double marksGot = 0;
        int correctAnswers = 0;
        int attempted = 0;
        for (QuestionCollaboration q : questions) {
            //single questions
            QuestionCollaboration question = this.service.get(q.getQuesId());
            if (question.getAnswer().equals(q.getGivenAnswer())) {
                //correct
                correctAnswers++;
                double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / questions.size();
                //       this.questions[0].quiz.maxMarks / this.questions.length;
                marksGot += marksSingle;
            }
            if (q.getGivenAnswer() != null) {
                attempted++;
            }

        };


        Map<String, Object> map = new HashMap<>();
        map.put("marksGot", marksGot);
        map.put("correctAnswers", correctAnswers);
        map.put("attempted", attempted);
        return ResponseEntity.ok(map);

    }

}

