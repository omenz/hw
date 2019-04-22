import { Component, OnInit } from '@angular/core';
import {QuizService} from "../../../core/services/quiz.service";
import {Router} from '@angular/router';

@Component({
  selector: 'app-create-quiz',
  templateUrl: './create-quiz.component.html',
  styleUrls: ['./create-quiz.component.scss']
})
export class CreateQuizComponent implements OnInit {

  private name: string = "";

  constructor(private quizService: QuizService,
              private router: Router,
              ) { }

  ngOnInit() {
  }

  updateName(name) {
    this.name = name;
  }

  createQuiz() {
    this.quizService.createQuiz({id: null, name: this.name, forms: []})
      .subscribe(() => this.router.navigateByUrl('manage-quizzes'))
  }

}
