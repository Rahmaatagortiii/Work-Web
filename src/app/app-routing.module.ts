import { WeatherApiComponent } from './core/weather-api/weather-api.component';
import { InstructionsComponent } from './core/instructions/instructions.component';
import { LoadQuizComponent } from './core/load-quiz/load-quiz.component';
import { QuizComponent } from './core/quiz/quiz.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './core/home-page/home-page.component';
import { StreamComponent } from './live-stream/stream/stream.component';
import { StartComponent } from './core/start/start.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'live-stream', component: StreamComponent },
  { path: 'quiz', component: QuizComponent },
  {path: 'quiz/:catId',component: LoadQuizComponent},
  {path: 'quiz/instructions/:qid',component: InstructionsComponent},
  {path: 'quiz/start/:qid',component: StartComponent},
  {path: 'weather',component: WeatherApiComponent},
  { path: '**', redirectTo: '', pathMatch: 'full' },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
