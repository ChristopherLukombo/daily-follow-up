import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { PatientsComponent } from "./components/patients/patients.component";
import { PatientComponent } from "./components/patient/patient.component";

const routes: Routes = [
  { path: "patient", component: PatientsComponent },
  { path: "patient/details", component: PatientComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
