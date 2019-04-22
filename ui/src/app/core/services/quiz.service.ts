import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Quiz, QuizQuestion} from "../domain/model/quiz";

const API_URL: string = "/api/quiz";

@Injectable({
  providedIn: 'root'
})
export class QuizService {

  constructor(private httpClient: HttpClient) { }

  getQuizzes(): Observable<Array<Quiz>> {
    return this.httpClient.get<Array<Quiz>>(API_URL);
  }

  getQuiz(id: number): Observable<Quiz> {
    return this.httpClient.get<Quiz>(`${API_URL}/${id}`);
  }

  saveAnswer(questionId: number, answer: string): Observable<QuizQuestion> {
    return this.httpClient.post<QuizQuestion>(`${API_URL}/question/${questionId}/answer`, [{answer}]);
  }
}
