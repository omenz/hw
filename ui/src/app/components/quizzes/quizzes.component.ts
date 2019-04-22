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
  protected languages = new Set<string>();
  protected selectedLanguage: string;

  constructor(private quizService: QuizService) { }

  ngOnInit() {
    this.quizzes = this.quizService.getQuizzes();
    this.quizzes.subscribe(quizzes => {
      quizzes.forEach(quiz => {
        quiz.forms.forEach(form => this.languages.add(form.language));
        this.selectedLanguage = this.languages.values().next().value;
      });
      console.log(quizzes);
    });
  }

  private onLanguageSelect(language: string) {
    this.selectedLanguage = language;
  }

  private quizHasSelectedLanguage(quiz: Quiz): boolean {
    let language =  this.selectedLanguage;
    return !!quiz.forms.find(form => form.language == language);
  }
}
