import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Quiz} from "../domain/model/quiz";

const API_URL: string = "/api/quiz";

@Injectable({
  providedIn: 'root'
})
export class QuizService {

  constructor(private httpClient: HttpClient) { }

  getQuizzes(): Observable<Array<Quiz>> {
    return this.httpClient.get<Array<Quiz>>(API_URL);
  }
}
