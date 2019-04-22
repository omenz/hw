import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {QuizzesComponent} from "./components/quizzes/quizzes.component";
import {QuizComponent} from "./components/quizzes/quiz/quiz.component";

const routes: Routes = [
  {path: '', component: QuizzesComponent},
  {path: 'quiz/:id/:language', component: QuizComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
