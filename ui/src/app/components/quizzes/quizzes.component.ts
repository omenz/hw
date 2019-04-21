import {Component, OnInit} from '@angular/core';
import {QuizService} from "../../core/services/quiz.service";
import {Quiz} from "../../core/domain/model/quiz";
import {Observable} from "rxjs";

@Component({
  selector: 'app-quizzes',
  templateUrl: './quizzes.component.html',
  styleUrls: ['./quizzes.component.sass']
})
export class QuizzesComponent implements OnInit {

  private quizzes: Observable<Array<Quiz>>;

  constructor(private quizService: QuizService) { }

  ngOnInit() {
    this.quizzes = this.quizService.getQuizzes();
    this.quizzes.subscribe(quiz => console.log(quiz));
  }

}
