import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizComponent } from './quiz.component';
import {AppRoutingModule} from "../../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {QuizzesComponent} from "../quizzes.component";
import {FormsModule} from "@angular/forms";
import {FormsComponent} from "../forms/forms.component";
import {ManageQuizzesComponent} from "../../manage-quizzes/manage-quizzes.component";
import {CreateQuizComponent} from "../../manage-quizzes/create-quiz/create-quiz.component";
import {EditQuizComponent} from "../../manage-quizzes/edit-quiz/edit-quiz.component";

describe('QuizComponent', () => {
  let component: QuizComponent;
  let fixture: ComponentFixture<QuizComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        QuizComponent,
        QuizzesComponent,
        FormsComponent,
        ManageQuizzesComponent,
        CreateQuizComponent,
        EditQuizComponent,
      ],
      imports: [
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuizComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});