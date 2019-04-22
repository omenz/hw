import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizzesComponent } from './quizzes.component';
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "../../app-routing.module";
import {QuizComponent} from "./quiz/quiz.component";
import {FormsModule} from "@angular/forms";

describe('QuizzesComponent', () => {
  let component: QuizzesComponent;
  let fixture: ComponentFixture<QuizzesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        QuizzesComponent,
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
    fixture = TestBed.createComponent(QuizzesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
