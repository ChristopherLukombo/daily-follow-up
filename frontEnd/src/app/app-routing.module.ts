import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { PatientsComponent } from "./components/patients/patients.component";
import { PatientComponent } from "./components/patient/patient.component";
import { PatientHistoryComponent } from "./components/patient-history/patient-history.component";
import { PatientAddComponent } from "./components/patient-add/patient-add.component";
import { LoginComponent } from "./components/login/login.component";

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "patient", component: PatientsComponent },
  { path: "patient/details", component: PatientComponent },
  { path: "patient/details/history", component: PatientHistoryComponent },
  { path: "patient/add", component: PatientAddComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
