import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {QuizzesComponent} from "./components/quizzes/quizzes.component";
import {QuizComponent} from "./components/quizzes/quiz/quiz.component";
import {FormsComponent} from "./components/quizzes/forms/forms.component";
import {ManageQuizzesComponent} from "./components/manage-quizzes/manage-quizzes.component";
import {EditQuizComponent} from "./components/manage-quizzes/edit-quiz/edit-quiz.component";
import {CreateQuizComponent} from "./components/manage-quizzes/create-quiz/create-quiz.component";

const routes: Routes = [
  {path: 'quizzes', component: QuizzesComponent},
  {path: 'quizzes/:id/forms', component: FormsComponent},
  {path: 'quizzes/:quizId/forms/:formId', component: QuizComponent},
  {path: 'manage-quizzes', component: ManageQuizzesComponent},
  {path: 'manage-quizzes/create', component: CreateQuizComponent},
  {path: 'manage-quizzes/edit/:id', component: EditQuizComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
