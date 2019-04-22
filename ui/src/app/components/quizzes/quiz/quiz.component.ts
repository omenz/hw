import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {QuizService} from "../../../core/services/quiz.service";
import {Observable, Subject} from "rxjs";
import {Quiz, QuizAnswer, QuizForm} from "../../../core/domain/model/quiz";
import {debounceTime, distinctUntilChanged} from "rxjs/internal/operators";

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.scss']
})
export class QuizComponent implements OnInit {

  private answerChanged: Subject<Answer> = new Subject<Answer>();
  private quiz: Observable<Quiz>;
  private selectedLanguage: string;
  private answers: Map<number, QuizAnswer> = new Map();
  private saveInProgress: boolean = false;

  constructor(private route: ActivatedRoute,
              private quizService: QuizService,
  ) {
    this.answerChanged
      .pipe(debounceTime(500), distinctUntilChanged())
      .subscribe(model => {
        this.saveInProgress = true;
        this.quizService.saveAnswer(model.questionId, model.answer)
          .subscribe(quizQuestion => {
            console.log(quizQuestion);
            this.saveInProgress = false;
          })
      });
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
        this.selectedLanguage = params['language'];
        this.quiz = this.quizService.getQuiz(params['id']);
        this.quiz.subscribe(quiz => {
          this.findFormForSelectedLanguage(quiz).section.quizQuestions.forEach(question => {
            let answer = question.answers[0];
            this.answers.set(question.id, answer ? answer : QuizAnswer.new());
          })
        })
      }
    );
  }

  findFormForSelectedLanguage(quiz: Quiz): QuizForm {
    return quiz.forms.find(form => form.language == this.selectedLanguage);
  }

  formHasQuestions(): boolean {
    return !!this.answers.size
  }

  saveAnswer(questionId: number, answer: string): void {
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
