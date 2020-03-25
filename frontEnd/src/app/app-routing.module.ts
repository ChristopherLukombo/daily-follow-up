import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { PatientsComponent } from "./components/patients/patients.component";

const routes: Routes = [{ path: "patients", component: PatientsComponent }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
