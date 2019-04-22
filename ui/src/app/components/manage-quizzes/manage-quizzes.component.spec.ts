import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageQuizzesComponent } from './manage-quizzes.component';
import {AppRoutingModule} from "../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {QuizzesComponent} from "../quizzes/quizzes.component";
import {FormsComponent} from "../quizzes/forms/forms.component";
import {QuizComponent} from "../quizzes/quiz/quiz.component";

describe('ManageQuizzesComponent', () => {
  let component: ManageQuizzesComponent;
  let fixture: ComponentFixture<ManageQuizzesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        ManageQuizzesComponent,
        QuizzesComponent,
        FormsComponent,
        QuizComponent,
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
    fixture = TestBed.createComponent(ManageQuizzesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
