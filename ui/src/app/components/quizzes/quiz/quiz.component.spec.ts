import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizComponent } from './quiz.component';
import {AppRoutingModule} from "../../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {QuizzesComponent} from "../quizzes.component";
import {FormsModule} from "@angular/forms";

describe('QuizComponent', () => {
  let component: QuizComponent;
  let fixture: ComponentFixture<QuizComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        QuizComponent,
        QuizzesComponent,
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
