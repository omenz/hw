import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Quiz, QuizForm, QuizQuestion} from "../domain/model/quiz";

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

  getQuizForms(id: number): Observable<Array<QuizForm>> {
    return this.httpClient.get<Array<QuizForm>>(`${API_URL}/${id}/form`);
  }

  saveAnswer(questionId: number, answer: string): Observable<QuizQuestion> {
    return this.httpClient.post<QuizQuestion>(`${API_URL}/question/${questionId}/answer`, [{answer}]);
  }

  deleteQuiz(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${API_URL}/${id}`);
  }

  createQuiz(quiz: Quiz): Observable<Quiz> {
    return this.httpClient.post<Quiz>(API_URL, quiz);
  }

  createForm(quizId: number, form: QuizForm): Observable<QuizForm> {
    return this.httpClient.post<QuizForm>(`${API_URL}/${quizId}/form`, form);
  }

  updateForm(id: number, form: QuizForm): Observable<QuizForm> {
    return this.httpClient.put<QuizForm>(`${API_URL}/form/${id}`, form);
  }

  deleteForm(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${API_URL}/form/${id}`);
  }
}
