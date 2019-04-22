import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateQuizComponent } from './create-quiz.component';
import {ManageQuizzesComponent} from "../manage-quizzes.component";
import {QuizzesComponent} from "../../quizzes/quizzes.component";
import {FormsComponent} from "../../quizzes/forms/forms.component";
import {QuizComponent} from "../../quizzes/quiz/quiz.component";
import {EditQuizComponent} from "../edit-quiz/edit-quiz.component";
import {AppRoutingModule} from "../../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";

describe('CreateQuizComponent', () => {
  let component: CreateQuizComponent;
  let fixture: ComponentFixture<CreateQuizComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        ManageQuizzesComponent,
        QuizzesComponent,
        FormsComponent,
        QuizComponent,
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
    fixture = TestBed.createComponent(CreateQuizComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
