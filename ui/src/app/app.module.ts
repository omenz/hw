import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { QuizzesComponent } from './components/quizzes/quizzes.component';
import {HttpClientModule} from "@angular/common/http";
import { QuizComponent } from './components/quizzes/quiz/quiz.component';
import {FormsModule} from "@angular/forms";
import { FormsComponent } from './components/quizzes/forms/forms.component';

@NgModule({
  declarations: [
    AppComponent,
    QuizzesComponent,
    QuizComponent,
    FormsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [
    HttpClientModule,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
