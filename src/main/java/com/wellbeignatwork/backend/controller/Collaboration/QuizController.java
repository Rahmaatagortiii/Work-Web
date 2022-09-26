package com.wellbeignatwork.backend.controller.Collaboration;

import com.wellbeignatwork.backend.entity.Collaboration.Category;
import com.wellbeignatwork.backend.entity.Collaboration.Quiz;
import com.wellbeignatwork.backend.service.Collaboration.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/quiz")
public class QuizController {

	   @Autowired
	    private QuizService quizService;

    //http://localhost:8081/Wellbeignatwork/quiz/
    @PostMapping("/")
    public ResponseEntity<Quiz> add(@RequestBody Quiz quiz) {
        return ResponseEntity.ok(this.quizService.addQuiz(quiz));
    }

    //update quiz
    //http://localhost:8081/Wellbeignatwork/quiz/
    @PutMapping("/")
    public ResponseEntity<Quiz> update(@RequestBody Quiz quiz) {
        return ResponseEntity.ok(this.quizService.updateQuiz(quiz));
    }

    //get quiz
    //http://localhost:8081/Wellbeignatwork/quiz/
    @GetMapping("/")
    public ResponseEntity<?> quizzes() {
        return ResponseEntity.ok(this.quizService.getQuizzes());
    }

    //get single quiz
    //http://localhost:8081/Wellbeignatwork/quiz/1
    @GetMapping("/{qid}")
    public Quiz quiz(@PathVariable("qid") Long qid) {
        return this.quizService.getQuiz(qid);
    }

    //delete the quiz
    //http://localhost:8081/Wellbeignatwork/quiz/1
    @DeleteMapping("/{qid}")
    public void delete(@PathVariable("qid") Long qid) {
        this.quizService.deleteQuiz(qid);
    }

    //http://localhost:8081/Wellbeignatwork/quiz/category/2
    @GetMapping("/category/{cid}")
    public List<Quiz> getQuizzesOfCategory(@PathVariable("cid") Long cid) {
        Category category = new Category();
        category.setCid(cid);
        return this.quizService.getQuizzesOfCategory(category);
    }

    //get active quizzes
    //http://localhost:8081/Wellbeignatwork/quiz/active
    @GetMapping("/active")
    public List<Quiz> getActiveQuizzes() {
        return this.quizService.getActiveQuizzes();
    }

    //get active quizzes of category
    //http://localhost:8081/Wellbeignatwork/quiz/category/active/2
    @GetMapping("/category/active/{cid}")
    public List<Quiz> getActiveQuizzes(@PathVariable("cid") Long cid) {
        Category category = new Category();
        category.setCid(cid);
        return this.quizService.getActiveQuizzesOfCategory(category);
    }

}
