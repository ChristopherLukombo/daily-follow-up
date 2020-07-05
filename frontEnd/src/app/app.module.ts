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
import { NgSelectModule } from "@ng-select/ng-select";

import { AppComponent } from "./app.component";
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

import { FoodNavbarComponent } from "./components/navbar-vertical/food-navbar/food-navbar.component";
import { FormMealAddComponent } from "./components/food-components/meals-add/form-meal-add/form-meal-add.component";
import { InfosMealComponent } from "./components/food-components/meals-add/infos-meal/infos-meal.component";
import { MealsAddComponent } from "./components/food-components/meals-add/meals-add.component";
import { MealsComponent } from "./components/food-components/meals/meals.component";
import { ListMealsComponent } from "./components/food-components/meals/list-meals/list-meals.component";
import { MenuAddComponent } from "./components/food-components/menu-add/menu-add.component";
import { MenuWeeksComponent } from "./components/food-components/menu-weeks/menu-weeks.component";
import { ContentsDayMenuComponent } from "./components/food-components/menu-weeks/contents-day-menu/contents-day-menu.component";
import { ReplacementsCardComponent } from "./components/food-components/menu-weeks/replacements-card/replacements-card.component";
import { MenuCurrentsComponent } from "./components/food-components/menu-currents/menu-currents.component";
import { CurrentWeeksComponent } from "./components/food-components/menu-currents/current-weeks/current-weeks.component";
import { DietAddComponent } from "./components/food-components/diet-add/diet-add.component";
import { MenuDeclineComponent } from "./components/food-components/menu-decline/menu-decline.component";
import { MenuWeeksLockComponent } from "./components/food-components/menu-decline/menu-weeks-lock/menu-weeks-lock.component";
import { ContentsDayMenuLockComponent } from "./components/food-components/menu-decline/menu-weeks-lock/contents-day-menu-lock/contents-day-menu-lock.component";
import { ReplacementsCardLockComponent } from "./components/food-components/menu-decline/menu-weeks-lock/replacements-card-lock/replacements-card-lock.component";

import { RoleNavbarComponent } from "./components/navbar-vertical/role-navbar/role-navbar.component";
import { UserAddComponent } from "./components/user-components/user-add/user-add.component";
import { ResetUserPasswordComponent } from "./components/user-components/reset-user-password/reset-user-password.component";
import { UsersComponent } from "./components/user-components/users/users.component";
import { ListCaregiversComponent } from "./components/user-components/users/list-caregivers/list-caregivers.component";
import { ListNutritionistsComponent } from "./components/user-components/users/list-nutritionists/list-nutritionists.component";
import { DetailUserComponent } from "./components/user-components/users/list-nutritionists/detail-user/detail-user.component";
import { DetailCaregiverComponent } from "./components/user-components/users/list-caregivers/detail-caregiver/detail-caregiver.component";

import { ClinicNavbarComponent } from "./components/navbar-vertical/clinic-navbar/clinic-navbar.component";
import { FloorsComponent } from "./components/clinic-components/floors/floors.component";
import { DetailFloorComponent } from "./components/clinic-components/floors/detail-floor/detail-floor.component";
import { FloorAddComponent } from "./components/clinic-components/floor-add/floor-add.component";

import { LoaderComponent } from "./components/loader/loader.component";
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
  SearchInsidePipe,
  OrderInsidePipe,
} from "./utils/pipes/search.pipe";
import {
  getInitialsPipe,
  getActionPatientPipe,
  truncateStringPipe,
  getFloorNamePipe,
} from "./utils/pipes/string-utils.pipe";
import { DeclinedMenuTemplateComponent } from "./components/food-components/menu-decline/declined-menu-template/declined-menu-template.component";
import { DiffDeclinedContentDayComponent } from "./components/food-components/menu-decline/declined-menu-template/diff-declined-content-day/diff-declined-content-day.component";
import { MenuDeclinedEditComponent } from "./components/food-components/menu-declined-edit/menu-declined-edit.component";
import { MenuWeeksEditComponent } from "./components/food-components/menu-weeks-edit/menu-weeks-edit.component";
import { ContentsDayMenuEditComponent } from "./components/food-components/menu-weeks-edit/contents-day-menu-edit/contents-day-menu-edit.component";
import { ReplacementsCardEditComponent } from "./components/food-components/menu-weeks-edit/replacements-card-edit/replacements-card-edit.component";
import { MealEditComponent } from "./components/food-components/meal/meal-edit/meal-edit.component";
import { DetailMealComponent } from "./components/food-components/meals/detail-meal/detail-meal.component";
import { FormMealEditComponent } from "./components/food-components/meal/meal-edit/form-meal-edit/form-meal-edit.component";
import { OrdersComponent } from "./components/order-components/orders/orders.component";
import { OrderNavbarComponent } from "./components/navbar-vertical/order-navbar/order-navbar.component";
import { PictureMealEditComponent } from "./components/food-components/meal/meal-edit/picture-meal-edit/picture-meal-edit.component";
import { StatisticsComponent } from "./components/statistics/statistics/statistics.component";
import { PatientsPerAllergyComponent } from "./components/statistics/statistics/patients-per-allergy/patients-per-allergy.component";
import { PatientsPerDietComponent } from "./components/statistics/statistics/patients-per-diet/patients-per-diet.component";
import { PatientsByStatusComponent } from "./components/statistics/statistics/patients-by-status/patients-by-status.component";
import { OrderPerDayComponent } from "./components/statistics/statistics-food/order-per-day/order-per-day.component";
import { TopTrendyDietsComponent } from "./components/statistics/statistics-food/top-trendy-diets/top-trendy-diets.component";
import { TopTrendyContentsComponent } from "./components/statistics/statistics-food/top-trendy-contents/top-trendy-contents.component";
import { StatisticsNavbarComponent } from "./components/navbar-vertical/statistics-navbar/statistics-navbar.component";
import { StatisticsFoodComponent } from "./components/statistics/statistics-food/statistics-food.component";
import { MenuAllComponent } from './components/food-components/menu-all/menu-all.component';
import { MenuEditComponent } from './components/food-components/menu-edit/menu-edit.component';
import { DietEditComponent } from './components/food-components/diet-edit/diet-edit.component';

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
    SearchInsidePipe,
    OrderInsidePipe,
    HighLightPipe,
    AlertWarningComponent,
    CommentPatientComponent,
    PatientNavbarComponent,
    getInitialsPipe,
    truncateStringPipe,
    getFloorNamePipe,
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
    MenuAddComponent,
    MenuWeeksComponent,
    ContentsDayMenuComponent,
    UsersComponent,
    RoleNavbarComponent,
    ListCaregiversComponent,
    UserAddComponent,
    MealsComponent,
    ListMealsComponent,
    ReplacementsCardComponent,
    MenuCurrentsComponent,
    CurrentWeeksComponent,
    FloorsComponent,
    ClinicNavbarComponent,
    DetailFloorComponent,
    ResetUserPasswordComponent,
    ListNutritionistsComponent,
    DetailUserComponent,
    DetailCaregiverComponent,
    FloorAddComponent,
    DietAddComponent,
    MenuDeclineComponent,
    MenuWeeksLockComponent,
    ContentsDayMenuLockComponent,
    ReplacementsCardLockComponent,
    DeclinedMenuTemplateComponent,
    DiffDeclinedContentDayComponent,
    MenuDeclinedEditComponent,
    MenuWeeksEditComponent,
    ContentsDayMenuEditComponent,
    ReplacementsCardEditComponent,
    MealEditComponent,
    DetailMealComponent,
    FormMealEditComponent,
    OrdersComponent,
    OrderNavbarComponent,
    PictureMealEditComponent,
    StatisticsComponent,
    PatientsPerAllergyComponent,
    PatientsPerDietComponent,
    PatientsByStatusComponent,
    OrderPerDayComponent,
    TopTrendyDietsComponent,
    TopTrendyContentsComponent,
    StatisticsNavbarComponent,
    StatisticsFoodComponent,
    MenuAllComponent,
    MenuEditComponent,
    DietEditComponent,
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
    NgSelectModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    AuthGuard,
    LoginService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
