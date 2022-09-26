import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private _http: HttpClient) {}


  public getQuestionsOfQuiz(qid:any) {
    return this._http.get(`http://localhost:8081/Wellbeignatwork/question/quiz/all/${qid}`);
  }

  public getQuestionsOfQuizForTest(qid:any) {
    return this._http.get(`http://localhost:8081/Wellbeignatwork/question/quiz/${qid}`);
  }

  //add question
  public addQuestion(question:any) {
    return this._http.post(`http://localhost:8081/Wellbeignatwork/question/`, question);
  }
  //delete question
  public deleteQuestion(questionId:any) {
    return this._http.delete(`http://localhost:8081/Wellbeignatwork/question/${questionId}`);
  }

  //eval quiz
  public evalQuiz(questions:any) {
    return this._http.post(`http://localhost:8081/Wellbeignatwork/question/eval-quiz`, questions);
  }


}
