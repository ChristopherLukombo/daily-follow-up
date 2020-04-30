import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

import { AppRoutingModule } from "./app-routing.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { AuthGuard } from "./utils/helpers/auth.guard";

import { JwtInterceptor } from "./utils/helpers/jwt.interceptor";
import { NgxPaginationModule } from "ngx-pagination";
import { ToastrModule } from "ngx-toastr";

import { AppComponent } from "./app.component";
import { LoaderComponent } from "./components/loader/loader.component";
import { NavbarComponent } from "./components/navbar/navbar.component";
import { MenuComponent } from "./components/navbar/menu/menu.component";
import { PatientsComponent } from "./components/patients/patients.component";
import { ListPatientsComponent } from "./components/patients/list-patients/list-patients.component";
import { DetailPatientComponent } from "./components/patients/detail-patient/detail-patient.component";
import { PatientComponent } from "./components/patient/patient.component";
import { InfosPatientComponent } from "./components/patient/infos-patient/infos-patient.component";
import { FoodPatientComponent } from "./components/patient/food-patient/food-patient.component";
import { ActivityPatientComponent } from "./components/patient/patient-history/activity-patient/activity-patient.component";
import { PatientHistoryComponent } from "./components/patient/patient-history/patient-history.component";
import { PatientAddComponent } from "./components/patient-add/patient-add.component";
import { LoginComponent } from "./components/login/login.component";
import { CommentPatientComponent } from "./components/patient/food-patient/comment-patient/comment-patient.component";
import { PatientNavbarComponent } from "./components/navbar-vertical/patient-navbar/patient-navbar.component";
import { PatientsImportComponent } from "./components/patients-import/patients-import.component";
import { PatientsImportInfosComponent } from "./components/patients-import/patients-import-infos/patients-import-infos.component";
import { PatientImportResultComponent } from "./components/patients-import/patient-import-result/patient-import-result.component";
import { PatientsOldComponent } from "./components/patients-old/patients-old.component";

import { AlertErrorComponent } from "./components/utils-components/alert-error/alert-error.component";
import { AlertWarningComponent } from "./components/utils-components/alert-warning/alert-warning.component";
import { ModalDangerComponent } from "./components/utils-components/modal-danger/modal-danger.component";

import { LoginService } from "./services/login/login.service";

import { DetermineAgePipe } from "./utils/pipes/determine-age.pipe";
import {
  SearchPipe,
  HighLightPipe,
  OrderPipe,
} from "./utils/pipes/search.pipe";
import {
  getInitialsPipe,
  getActionPatientPipe,
} from "./utils/pipes/string-utils.pipe";
import { RoomAvailableSelectorComponent } from './components/patient-add/room-available-selector/room-available-selector.component';

@NgModule({
  declarations: [
    AppComponent,
    LoaderComponent,
    NavbarComponent,
    MenuComponent,
    PatientsComponent,
    ListPatientsComponent,
    DetailPatientComponent,
    PatientComponent,
    InfosPatientComponent,
    FoodPatientComponent,
    ActivityPatientComponent,
    PatientHistoryComponent,
    PatientAddComponent,
    LoginComponent,
    AlertErrorComponent,
    DetermineAgePipe,
    SearchPipe,
    HighLightPipe,
    AlertWarningComponent,
    CommentPatientComponent,
    PatientNavbarComponent,
    getInitialsPipe,
    OrderPipe,
    getActionPatientPipe,
    PatientsImportComponent,
    PatientsImportInfosComponent,
    PatientImportResultComponent,
    PatientsOldComponent,
    ModalDangerComponent,
    RoomAvailableSelectorComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FontAwesomeModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxPaginationModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      progressBar: true,
      closeButton: true,
      enableHtml: true,
      preventDuplicates: true,
      countDuplicates: true,
    }),
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    AuthGuard,
    LoginService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
