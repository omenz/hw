import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {QuizService} from "../../../core/services/quiz.service";
import {Observable} from "rxjs";
import {Quiz, QuizForm} from "../../../core/domain/model/quiz";

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.scss']
})
export class QuizComponent implements OnInit {

  private quiz: Observable<Quiz>;
  private selectedLanguage: string;

  constructor(private route: ActivatedRoute,
              private quizService: QuizService,
  ) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
        this.selectedLanguage = params['language'];
        this.quiz = this.quizService.getQuiz(params['id'])
      }
    );
  }

  findFormForSelectedLanguage(quiz: Quiz): QuizForm {
    return quiz.forms.find(form => form.language == this.selectedLanguage);
  }
}
