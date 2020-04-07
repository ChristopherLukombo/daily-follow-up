import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { HttpClientModule } from "@angular/common/http";

import { AuthGuard } from "./utils/helpers/auth.guard";

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
import { HistoryPatientComponent } from "./components/patient/history-patient/history-patient.component";
import { PatientHistoryComponent } from "./components/patient-history/patient-history.component";
import { PatientAddComponent } from "./components/patient-add/patient-add.component";
import { LoginComponent } from "./components/login/login.component";
import { AlertErrorComponent } from "./components/utils-components/alert-error/alert-error.component";
import { LoginService } from "./services/login/login.service";

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
    HistoryPatientComponent,
    PatientHistoryComponent,
    PatientAddComponent,
    LoginComponent,
    AlertErrorComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FontAwesomeModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [AuthGuard, LoginService],
  bootstrap: [AppComponent],
})
export class AppModule {}
