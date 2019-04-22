import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {QuizzesComponent} from "./components/quizzes/quizzes.component";
import {QuizComponent} from "./components/quizzes/quiz/quiz.component";
import {FormsComponent} from "./components/quizzes/forms/forms.component";

const routes: Routes = [
  {path: 'quizzes', component: QuizzesComponent},
  {path: 'quizzes/:id/forms', component: FormsComponent},
  {path: 'quizzes/:quizId/forms/:formId', component: QuizComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
