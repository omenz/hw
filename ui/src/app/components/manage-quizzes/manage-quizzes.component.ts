import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {Quiz} from "../../core/domain/model/quiz";
import {QuizService} from "../../core/services/quiz.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-manage-quizzes',
  templateUrl: './manage-quizzes.component.html',
  styleUrls: ['./manage-quizzes.component.scss']
})
export class ManageQuizzesComponent implements OnInit {

  protected quizzes: Observable<Array<Quiz>>;

  constructor(private quizService: QuizService,
              private toastr: ToastrService,
  ) { }

  ngOnInit() {
    this.fetchQuizzes();
  }

  fetchQuizzes() {
    this.quizzes = this.quizService.getQuizzes();
  }

  deleteQuiz(quiz: Quiz) {
    if (confirm('Delete quiz: "' + quiz.name+ '"?')) {
      this.quizService.deleteQuiz(quiz.id).subscribe(() => this.fetchQuizzes())
      this.toastr.success('Quiz deleted!');
    }
  }
}
