import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {QuizService} from "../../../core/services/quiz.service";
import {Observable, Subject} from "rxjs";
import {Quiz, QuizAnswer, QuizForm, QuizSection} from "../../../core/domain/model/quiz";
import {debounceTime, distinctUntilChanged} from "rxjs/internal/operators";

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.scss']
})
export class QuizComponent implements OnInit {

  private answerChanged: Subject<Answer> = new Subject<Answer>();
  private quiz: Observable<Quiz>;
  private form: QuizForm;
  private answers: Map<number, QuizAnswer> = new Map();
  private saveInProgress: boolean = false;
  private saved: boolean = false;

  constructor(private route: ActivatedRoute,
              private quizService: QuizService,
  ) {
    this.answerChanged
      .pipe(debounceTime(500), distinctUntilChanged())
      .subscribe(model => {
        this.quizService.saveAnswer(model.questionId, model.answer)
          .subscribe(() => {
            this.saveInProgress = false;
            this.saved = true;
          })
      });
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
        this.quiz = this.quizService.getQuiz(params['quizId']);
        this.quiz.subscribe(quiz => {
          this.form = quiz.forms.find(form => form.id == params['formId']);
          this.form.section.quizQuestions.forEach(question => {
            let answer = question.answers[0];
            this.answers.set(question.id, answer ? answer : QuizAnswer.new());
          })
        })
      }
    );
  }

  formHasQuestions(): boolean {
    return !!this.answers.size
  }

  saveAnswer(questionId: number, answer: string): void {
    this.saved = false;
    this.saveInProgress = true;
    this.answerChanged.next({questionId, answer});
  }
}

class Answer {
  questionId: number;
  answer: string;

  constructor(questionId: number, answer: string) {
    this.questionId = questionId;
    this.answer = answer;
  }
}
