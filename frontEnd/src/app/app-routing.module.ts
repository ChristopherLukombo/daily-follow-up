import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { AuthGuard } from "./utils/helpers/auth.guard";
import { PatientsComponent } from "./components/patients/patients.component";
import { PatientComponent } from "./components/patient/patient.component";
import { PatientHistoryComponent } from "./components/patient-history/patient-history.component";
import { PatientAddComponent } from "./components/patient-add/patient-add.component";
import { LoginComponent } from "./components/login/login.component";
import { PatientsImportComponent } from "./components/patients-import/patients-import.component";

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "patient", component: PatientsComponent, canActivate: [AuthGuard] },
  {
    path: "patient/details",
    component: PatientComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "patient/details/history",
    component: PatientHistoryComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "patient/add",
    component: PatientAddComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "patient/import",
    component: PatientsImportComponent,
    canActivate: [AuthGuard],
  },

  // plus tard rediriger vers l'ecran d'accueil des plats
  //{ path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
