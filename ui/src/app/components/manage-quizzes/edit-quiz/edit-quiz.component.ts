import {Component, OnInit} from '@angular/core';
import {Quiz, QuizForm, QuizQuestion} from "../../../core/domain/model/quiz";
import {ActivatedRoute} from "@angular/router";
import {QuizService} from "../../../core/services/quiz.service";

@Component({
  selector: 'app-edit-quiz',
  templateUrl: './edit-quiz.component.html',
  styleUrls: ['./edit-quiz.component.scss']
})
export class EditQuizComponent implements OnInit {
  private quiz: Quiz;
  private quizId: number;

  constructor(private route: ActivatedRoute,
              private quizService: QuizService,
  ) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
        this.quizId = params['id'];
        this.quizService.getQuiz(this.quizId).subscribe(quiz => this.quiz = quiz)
      }
    );
  }

  addForm(quizId: number) {
    this.quizService.createForm(quizId, {
      id: null,
      language: "",
      section: {id: null, quizQuestions: [], childSections: []}
    })
      .subscribe(form => this.quiz.forms.push(form))
  }

  updateForm(form: QuizForm) {
    this.quizService.updateForm(form.id, form).subscribe()
  }

  deleteForm(formId: number) {
    this.quizService.deleteForm(formId)
      .subscribe(() =>  this.quiz.forms = this.quiz.forms.filter(form => form.id != formId))
  }

  addQuestion(form: QuizForm) {
    form.section.quizQuestions.push({id: null, question: "", updateCount: 0, answers: []}) 
  }

  onQuestionChange(quizQuestion: QuizQuestion, question: string) {
    quizQuestion.question = question;
  }

  onFormLanguageChange(form: QuizForm, language: string) {
    form.language = language;
  }
}
