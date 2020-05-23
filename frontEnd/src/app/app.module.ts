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
import { ChartsModule } from "ng2-charts";

import { AppComponent } from "./app.component";
import { LoaderComponent } from "./components/loader/loader.component";
import { NavbarComponent } from "./components/navbar/navbar.component";
import { MenuComponent } from "./components/navbar/menu/menu.component";
import { PatientsComponent } from "./components/patient-components/patients/patients.component";
import { ListPatientsComponent } from "./components/patient-components/patients/list-patients/list-patients.component";
import { DetailPatientComponent } from "./components/patient-components/patients/detail-patient/detail-patient.component";
import { PatientComponent } from "./components/patient-components/patient/patient.component";
import { InfosPatientComponent } from "./components/patient-components/patient/infos-patient/infos-patient.component";
import { FoodPatientComponent } from "./components/patient-components/patient/food-patient/food-patient.component";
import { ActivityPatientComponent } from "./components/patient-components/patient/patient-history/activity-patient/activity-patient.component";
import { PatientHistoryComponent } from "./components/patient-components/patient/patient-history/patient-history.component";
import { PatientAddComponent } from "./components/patient-components/patient-add/patient-add.component";
import { LoginComponent } from "./components/login/login.component";
import { CommentPatientComponent } from "./components/patient-components/patient/food-patient/comment-patient/comment-patient.component";
import { PatientNavbarComponent } from "./components/navbar-vertical/patient-navbar/patient-navbar.component";
import { PatientsImportComponent } from "./components/patient-components/patients-import/patients-import.component";
import { PatientsImportInfosComponent } from "./components/patient-components/patients-import/patients-import-infos/patients-import-infos.component";
import { PatientImportResultComponent } from "./components/patient-components/patients-import/patient-import-result/patient-import-result.component";
import { PatientsOldComponent } from "./components/patient-components/patients-old/patients-old.component";
import { RoomAvailableSelectorComponent } from "./components/patient-components/patient-add/room-available-selector/room-available-selector.component";
import { ListRoomsAvailableComponent } from "./components/patient-components/patient-add/room-available-selector/list-rooms-available/list-rooms-available.component";
import { FormPatientAddComponent } from "./components/patient-components/patient-add/form-patient-add/form-patient-add.component";
import { PatientEditComponent } from "./components/patient-components/patient/patient-edit/patient-edit.component";
import { FormPatientEditComponent } from "./components/patient-components/patient/patient-edit/form-patient-edit/form-patient-edit.component";
import { FormMealAddComponent } from "./components/food-components/meals-add/form-meal-add/form-meal-add.component";
import { InfosMealComponent } from "./components/food-components/meals-add/infos-meal/infos-meal.component";

import { MealsAddComponent } from "./components/food-components/meals-add/meals-add.component";
import { FoodNavbarComponent } from "./components/navbar-vertical/food-navbar/food-navbar.component";

import { AlertErrorComponent } from "./components/utils-components/alert-error/alert-error.component";
import { AlertWarningComponent } from "./components/utils-components/alert-warning/alert-warning.component";
import { ModalDangerComponent } from "./components/utils-components/modal-danger/modal-danger.component";
import { Tabs, Tab } from "./components/utils-components/tabs/tabs.component";
import { Typeahead } from "./components/utils-components/typeahead/typeahead.component";

import { LoginService } from "./services/login/login.service";

import { DetermineAgePipe, GapPipe } from "./utils/pipes/number-utils.pipe";
import {
  SearchPipe,
  HighLightPipe,
  OrderPipe,
} from "./utils/pipes/search.pipe";
import {
  getInitialsPipe,
  getActionPatientPipe,
} from "./utils/pipes/string-utils.pipe";

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
    GapPipe,
    ListRoomsAvailableComponent,
    FormPatientAddComponent,
    PatientEditComponent,
    FormPatientEditComponent,
    MealsAddComponent,
    FoodNavbarComponent,
    FormMealAddComponent,
    InfosMealComponent,
    Tabs,
    Tab,
    Typeahead,
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
    ChartsModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    AuthGuard,
    LoginService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
