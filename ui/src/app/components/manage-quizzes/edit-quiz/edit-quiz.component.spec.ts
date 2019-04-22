import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditQuizComponent } from './edit-quiz.component';
import {AppRoutingModule} from "../../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {ManageQuizzesComponent} from "../manage-quizzes.component";
import {QuizzesComponent} from "../../quizzes/quizzes.component";
import {FormsComponent} from "../../quizzes/forms/forms.component";
import {QuizComponent} from "../../quizzes/quiz/quiz.component";
import {CreateQuizComponent} from "../create-quiz/create-quiz.component";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";

describe('EditQuizComponent', () => {
  let component: EditQuizComponent;
  let fixture: ComponentFixture<EditQuizComponent>;

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
        BrowserAnimationsModule,
        ToastrModule.forRoot(),
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditQuizComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
