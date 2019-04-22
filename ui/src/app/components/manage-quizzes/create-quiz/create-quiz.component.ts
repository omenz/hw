import { Component, OnInit } from '@angular/core';
import {QuizService} from "../../../core/services/quiz.service";
import {Router} from '@angular/router';
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-create-quiz',
  templateUrl: './create-quiz.component.html',
  styleUrls: ['./create-quiz.component.scss']
})
export class CreateQuizComponent implements OnInit {

  name: string = "";

  constructor(private quizService: QuizService,
              private router: Router,
              private toastr: ToastrService,
              ) { }

  ngOnInit() {
  }

  updateName(name) {
    this.name = name;
  }

  createQuiz() {
    this.quizService.createQuiz({id: null, name: this.name, forms: []})
      .subscribe(() => {
        this.toastr.success("Quiz created!");
        this.router.navigateByUrl('manage-quizzes')
      })
  }

}
