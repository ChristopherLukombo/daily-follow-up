import { Component, OnInit } from '@angular/core';
import { OrdersPerDay } from 'src/app/models/statistics/orders-per-day';
import { PatientsByStatus } from 'src/app/models/statistics/patients-by-status';
import { PatientsPerAllergy } from 'src/app/models/statistics/patients-per-allergy';
import { PatientsPerDiet } from 'src/app/models/statistics/patients-per-diet';
import { StatisticsService } from 'src/app/services/statistics/statistics.service';

@Component({
  selector: 'app-statistics-patients',
  templateUrl: './statistics-patients.component.html',
  styleUrls: ['./statistics-patients.component.scss']
})
export class StatisticsPatientsComponent implements OnInit {

  error: string;

  patientsPerAllergy: Array<PatientsPerAllergy>;
  patientsPerDiet: Array<PatientsPerDiet>;
  patientsByStatus: PatientsByStatus;
  orderPerStatus: Map<string, Array<OrdersPerDay>>;

  constructor(
    private statisticsService: StatisticsService) { }

  ngOnInit(): void {
    this.getPatientsPerAllergy();
    this.getPatientsPerDiet();
    this.getPatientsByStatus();
    this.getNumberOfOrdersPerDay();
  }

  private getPatientsPerAllergy(): void {
    this.statisticsService.getNumberOfPatientsPerAllergy()
      .subscribe(data => {
        this.patientsPerAllergy = data;
      }, error => {
        this.catchError(error);
      });
  }

  private getPatientsPerDiet(): void {
    this.statisticsService.getNumberOfPatientsPerDiet()
      .subscribe(data => {
        this.patientsPerDiet = data;
      }, error => {
        this.catchError(error);
      });
  }

  private getPatientsByStatus(): void {
    this.statisticsService.getNumberOfPatientsByStatus()
      .subscribe(data => {
        this.patientsByStatus = data;
      }, error => {
        this.catchError(error);
      });
  }

  private getNumberOfOrdersPerDay(): void {
    this.statisticsService.getNumberOfOrdersPerDay()
      .subscribe(data => {
        this.orderPerStatus = data;
      }, error => {
        this.catchError(error);
      });
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  catchError(error: number): void {
    if (error && error === 401) {
      this.error =
        "Le nom d'utilisateur et le mot de passe ne correspondent pas.";
    } else {
      this.error = "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
  /**
   * Suppression des msg d'erreurs
   */
  cleanErrorMessages(): void {
    this.error = undefined;
  }

}
