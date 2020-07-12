import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { FloorsComponent } from "./components/clinic-components/floors/floors.component";
import { DietAddComponent } from "./components/food-components/diet-add/diet-add.component";
import { MealEditComponent } from "./components/food-components/meal/meal-edit/meal-edit.component";
import { MealsAddComponent } from "./components/food-components/meals-add/meals-add.component";
import { MealsComponent } from "./components/food-components/meals/meals.component";
import { MenuAddComponent } from "./components/food-components/menu-add/menu-add.component";
import { MenuCurrentsComponent } from "./components/food-components/menu-currents/menu-currents.component";
import { MenuDeclineComponent } from "./components/food-components/menu-decline/menu-decline.component";
import { MenuDeclinedEditComponent } from "./components/food-components/menu-declined-edit/menu-declined-edit.component";
import { LoginComponent } from "./components/login/login.component";
import { OrdersComponent } from "./components/order-components/orders/orders.component";
import { PatientAddComponent } from "./components/patient-components/patient-add/patient-add.component";
import { PatientEditComponent } from "./components/patient-components/patient/patient-edit/patient-edit.component";
import { PatientHistoryComponent } from "./components/patient-components/patient/patient-history/patient-history.component";
import { PatientComponent } from "./components/patient-components/patient/patient.component";
import { PatientsImportComponent } from "./components/patient-components/patients-import/patients-import.component";
import { PatientsOldComponent } from "./components/patient-components/patients-old/patients-old.component";
import { PatientsComponent } from "./components/patient-components/patients/patients.component";
import { StatisticsComponent } from "./components/statistics/statistics/statistics.component";
import { ResetUserPasswordComponent } from "./components/user-components/reset-user-password/reset-user-password.component";
import { UserAddComponent } from "./components/user-components/user-add/user-add.component";
import { UsersComponent } from "./components/user-components/users/users.component";
import { AuthGuard } from "./utils/helpers/auth.guard";
import { StatisticsFoodComponent } from "./components/statistics/statistics-food/statistics-food.component";
import { MenuAllComponent } from "./components/food-components/menu-all/menu-all.component";
import { MenuEditComponent } from "./components/food-components/menu-edit/menu-edit.component";
import { DietEditComponent } from "./components/food-components/diet-edit/diet-edit.component";
import { OrderEditComponent } from "./components/order-components/order-edit/order-edit.component";
import { OrderAddComponent } from "./components/order-components/order-add/order-add.component";
import { DashboardComponent } from "./components/dashboard/dashboard.component";
import { Role } from "./models/user/role-enum";

const routes: Routes = [
  { path: "dashboard", component: DashboardComponent },
  { path: "login", component: LoginComponent },
  { path: "reset/password", component: ResetUserPasswordComponent },
  {
    path: "patient/all",
    component: PatientsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "patient/old",
    component: PatientsOldComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "patient/details",
    component: PatientComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "patient/details/edit",
    component: PatientEditComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.ROLE_ADMIN, Role.ROLE_DIET] },
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
    data: { roles: [Role.ROLE_ADMIN, Role.ROLE_DIET] },
  },
  {
    path: "patient/import",
    component: PatientsImportComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.ROLE_ADMIN, Role.ROLE_DIET] },
  },
  {
    path: "food/menu/currents",
    component: MenuCurrentsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "food/meal/all",
    component: MealsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "food/meal/add",
    component: MealsAddComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.ROLE_ADMIN, Role.ROLE_DIET] },
  },
  {
    path: "food/meal/details/edit",
    component: MealEditComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.ROLE_ADMIN, Role.ROLE_DIET] },
  },
  {
    path: "food/menu/add",
    component: MenuAddComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.ROLE_ADMIN, Role.ROLE_DIET] },
  },
  {
    path: "food/menu/edit",
    component: MenuEditComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.ROLE_ADMIN, Role.ROLE_DIET] },
  },
  {
    path: "food/menu/decline",
    component: MenuDeclineComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.ROLE_ADMIN, Role.ROLE_DIET] },
  },
  {
    path: "food/menu/decline/edit",
    component: MenuDeclinedEditComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.ROLE_ADMIN, Role.ROLE_DIET] },
  },
  {
    path: "food/menu/all",
    component: MenuAllComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "food/diet/add",
    component: DietAddComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "food/diet/edit",
    component: DietEditComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "clinic/floor/all",
    component: FloorsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "user/all",
    component: UsersComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "user/add",
    component: UserAddComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.ROLE_ADMIN, Role.ROLE_DIET] },
  },
  {
    path: "order/all",
    component: OrdersComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "order/add",
    component: OrderAddComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.ROLE_ADMIN, Role.ROLE_DIET] },
  },
  {
    path: "order/edit",
    component: OrderEditComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "statistics/patients",
    component: StatisticsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "statistics/food",
    component: StatisticsFoodComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "**",
    redirectTo: "/dashboard",
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
