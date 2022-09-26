import { AddReservationComponent } from './add-reservation/add-reservation.component';
import { ReservationComponent } from './reservation/reservation.component';
import { AddPublicityComponent } from './add-publicity/add-publicity.component';
import { PublicityComponent } from './publicity/publicity.component';
import { AddQuestionComponent } from './add-question/add-question.component';
import { AddQuizComponent } from './add-quiz/add-quiz.component';
import { ViewQuizzesComponent } from './view-quizzes/view-quizzes.component';
import { AddCategoryComponent } from './add-category/add-category.component';
import { ViewCategoriesComponent } from './view-categories/view-categories.component';
import { OfferComponent } from './offer/offer.component';
import { AddOfferComponent } from './add-offer/add-offer.component';
import { AddCollaborationComponent } from './add-collaboration/add-collaboration.component';
import { NgModule } from '@angular/core';
import { CollaborationsComponent } from './collaborations/collaborations.component';
import { RouterModule, Routes } from '@angular/router';
import { UpdateOfferComponent } from './update-offer/update-offer.component';
import { EspaceQuizComponent } from './espace-quiz/espace-quiz.component';
import { UpdateQuizComponent } from './update-quiz/update-quiz.component';
import { ViewQuizQuestionsComponent } from './view-quiz-questions/view-quiz-questions.component';
import { ProfileComponent } from './profile/profile.component';
import { SideBarCollabComponent } from './side-bar-collab/side-bar-collab.component';

const routes: Routes = [
  {
    path: "",
    children: [{ path: "collaborations", component: CollaborationsComponent },{ path: "Addcollaborations", component: AddCollaborationComponent },
    { path: "Offers", component: OfferComponent },{ path: "Addoffers", component: AddOfferComponent },{ path: "offers", component: OfferComponent },
    { path: "Update-offer", component: UpdateOfferComponent },{ path: "EspaceQuiz", component: EspaceQuizComponent },
    {path: 'categories',component: ViewCategoriesComponent},{path: 'add-category',component: AddCategoryComponent},
    {path: 'quizzes',component: ViewQuizzesComponent},{path: 'add-quiz',component: AddQuizComponent},
    {path: 'quiz/:qid',component: UpdateQuizComponent},{path: 'view-questions/:qid/:title',component: ViewQuizQuestionsComponent},
    {path: 'add-question/:qid/:title',component: AddQuestionComponent},{path: 'profile',component: ProfileComponent},
    {path: 'MenuCollab',component: SideBarCollabComponent},{path: 'Publicity',component: PublicityComponent},
    {path: 'AddPublicity',component: AddPublicityComponent},{path: 'Reservation',component: ReservationComponent},
    {path: 'AddReservation',component: AddReservationComponent},
  ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CollaborationRoutingModule { }
