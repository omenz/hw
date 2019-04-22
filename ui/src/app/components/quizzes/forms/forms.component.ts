import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {QuizService} from "../../../core/services/quiz.service";
import {QuizForm} from "../../../core/domain/model/quiz";
import {Observable} from "rxjs";

@Component({
  selector: 'app-forms',
  templateUrl: './forms.component.html',
  styleUrls: ['./forms.component.scss']
})
export class FormsComponent implements OnInit {

  private forms: Observable<Array<QuizForm>>;
  private quizId: number;

  constructor(
    private route: ActivatedRoute,
    private quizService: QuizService,
  ) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
        this.quizId = params['id'];
        this.forms = this.quizService.getQuizForms(this.quizId);
      }
    );
  }
}
