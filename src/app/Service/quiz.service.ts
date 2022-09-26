import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class QuizService {

  constructor(private _http: HttpClient) {}

  public quizzes() {
    return this._http.get(`http://localhost:8081/Wellbeignatwork/quiz/`);
  }

  //add quiz
  public addQuiz(quiz:any) {
    return this._http.post(`http://localhost:8081/Wellbeignatwork/quiz/`, quiz);
  }

  //delete quiz
  public deleteQuiz(qId:any) {
    return this._http.delete(`http://localhost:8081/Wellbeignatwork/quiz/${qId}`);
  }

  //get the single quiz

  public getQuiz(qId:any) {
    return this._http.get(`http://localhost:8081/Wellbeignatwork/quiz/${qId}`);
  }

  //update quiz
  public updateQuiz(quiz:any) {
    return this._http.put(`http://localhost:8081/Wellbeignatwork/quiz/`, quiz);
  }

  //get quizzes of category
  public getQuizzesOfCategory(cid:any) {
    return this._http.get(`http://localhost:8081/Wellbeignatwork/quiz/category/${cid}`);
  }
  //qet active quizzes
  public getActiveQuizzes() {
    return this._http.get(`http://localhost:8081/Wellbeignatwork/quiz/active`);
  }

  //get active quizzes of category
  public getActiveQuizzesOfCategory(cid:any) {
    return this._http.get(`http://localhost:8081/Wellbeignatwork/quiz/category/active/${cid}`);
  }


}
