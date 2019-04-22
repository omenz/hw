import {Component, OnInit} from '@angular/core';
import {QuizService} from "../../core/services/quiz.service";
import {Quiz} from "../../core/domain/model/quiz";
import {Observable} from "rxjs";

@Component({
  selector: 'app-quizzes',
  templateUrl: './quizzes.component.html',
  styleUrls: ['./quizzes.component.scss']
})
export class QuizzesComponent implements OnInit {

  protected quizzes: Observable<Array<Quiz>>;

  constructor(private quizService: QuizService,
              ) { }

  ngOnInit() {
      this.quizzes = this.quizService.getQuizzes();
  }

}
