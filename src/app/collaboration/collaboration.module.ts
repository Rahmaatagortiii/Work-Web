import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxMapboxGLModule } from 'ngx-mapbox-gl';
import { CollaborationRoutingModule } from './collaboration-routing.module';
import { CollaborationsComponent } from './collaborations/collaborations.component';
import { AddCollaborationComponent } from './add-collaboration/add-collaboration.component';
import { OfferComponent } from './offer/offer.component';
import { AddOfferComponent } from './add-offer/add-offer.component';
import { FormsModule } from '@angular/forms';
import { MapComponent } from './map/map.component';
import { environment } from '../../environments/environment';
import { UpdateCollaborationComponent } from './update-collaboration/update-collaboration.component';
import { UpdateOfferComponent } from './update-offer/update-offer.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { ShowCollaborationComponent } from './show-collaboration/show-collaboration.component';
import { ShowOffersComponent } from './show-offers/show-offers.component';
import { EspaceQuizComponent } from './espace-quiz/espace-quiz.component';
import { SlideBarQuizComponent } from './slide-bar-quiz/slide-bar-quiz.component';
import { PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatCardModule} from '@angular/material/card';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
// import {MatSelectModule} from '@angular/material';
import { MatSelectModule } from '@angular/material/select';
// import { MatFormFieldModule } from '@angular/material';
import { MatInputModule } from '@angular/material/input';
// import { MatFormFieldModule } from "@angular/material/form-field";
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { NgxUiLoaderHttpModule, NgxUiLoaderModule } from 'ngx-ui-loader';
import { ViewCategoriesComponent } from './view-categories/view-categories.component';
import { ViewQuizQuestionsComponent } from './view-quiz-questions/view-quiz-questions.component';
import { ViewQuizzesComponent } from './view-quizzes/view-quizzes.component';
import { UpdateQuizComponent } from './update-quiz/update-quiz.component';
import { AddCategoryComponent } from './add-category/add-category.component';
import { AddQuestionComponent } from './add-question/add-question.component';
import { AddQuizComponent } from './add-quiz/add-quiz.component';
import { ProfileComponent } from './profile/profile.component';
import { HttpClientModule } from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { SideBarCollabComponent } from './side-bar-collab/side-bar-collab.component';
import { SideBarOfferComponent } from './side-bar-offer/side-bar-offer.component';
import { PublicityComponent } from './publicity/publicity.component';
import { AddPublicityComponent } from './add-publicity/add-publicity.component';
import { RecaptchaModule, RecaptchaFormsModule } from "ng-recaptcha";
import { ReservationComponent } from './reservation/reservation.component';
import { AddReservationComponent } from './add-reservation/add-reservation.component';
import { PdfOffersComponent } from './pdf-offers/pdf-offers.component';
import { CollabSearchFilterPipe } from './pipes/collab-search-filter.pipe';

@NgModule({
  declarations: [
    CollaborationsComponent,
    AddCollaborationComponent,
    OfferComponent,
    AddOfferComponent,
    MapComponent,
    UpdateOfferComponent,
    UpdateCollaborationComponent,
    ShowCollaborationComponent,
    ShowOffersComponent,
    EspaceQuizComponent,
    SlideBarQuizComponent,
    ViewCategoriesComponent,
    ViewQuizQuestionsComponent,
    ViewQuizzesComponent,
    UpdateQuizComponent,
    AddCategoryComponent,
    AddQuestionComponent,
    AddQuizComponent,
    ProfileComponent,
    SideBarCollabComponent,
    SideBarOfferComponent,
    PublicityComponent,
    AddPublicityComponent,
    ReservationComponent,
    AddReservationComponent,
    PdfOffersComponent,
    CollabSearchFilterPipe,
  ],
  imports: [
    CommonModule,
    CollaborationRoutingModule,
    NgxPaginationModule,
    FormsModule,
    NgxMapboxGLModule.withConfig({
      accessToken: environment.mapbox.accessToken,
    }),
    MatSnackBarModule,
    MatCardModule,
    MatToolbarModule,MatIconModule,MatListModule,MatSelectModule,
    MatSlideToggleModule,
    // CKEditorModule
  MatProgressSpinnerModule,
  MatFormFieldModule,MatInputModule,
  MatButtonModule,
  FormsModule,
  HttpClientModule,
  MatSnackBarModule,
  MatCardModule,
  MatToolbarModule,MatIconModule,MatListModule,MatSelectModule,
  MatSlideToggleModule,
  // CKEditorModule
  MatProgressSpinnerModule,
  NgxUiLoaderModule,
  RecaptchaModule,
  ]
})
export class CollaborationModule { }
