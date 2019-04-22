import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {FormsComponent} from './forms.component';
import {AppRoutingModule} from "../../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {QuizzesComponent} from "../quizzes.component";
import {QuizComponent} from "../quiz/quiz.component";
import {FormsModule} from "@angular/forms";
import {ManageQuizzesComponent} from "../../manage-quizzes/manage-quizzes.component";

describe('FormsComponent', () => {
  let component: FormsComponent;
  let fixture: ComponentFixture<FormsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        FormsComponent,
        QuizzesComponent,
        QuizComponent,
        ManageQuizzesComponent,
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
    fixture = TestBed.createComponent(FormsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
