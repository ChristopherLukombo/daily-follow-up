import { Component, OnInit } from '@angular/core';
import { PatientsByStatus } from 'src/app/models/statistics/patients-by-status';
import { PatientsPerAllergy } from 'src/app/models/statistics/patients-per-allergy';
import { PatientsPerDiet } from 'src/app/models/statistics/patients-per-diet';
import { StatisticsService } from 'src/app/services/statistics/statistics.service';

export interface Ti {
  data: number[];
  label: string;
}

@Component({
  selector: 'app-statistics-patients',
  templateUrl: './statistics-patients.component.html',
  styleUrls: ['./statistics-patients.component.scss']
})
export class StatisticsPatientsComponent implements OnInit {


  // public barChartOptions: ChartOptions = {
  //   responsive: true,
  //   title: {
  //     display: true,
  //     fontSize: 13,
  //     text: "Composition nutritionnelle (100g)",
  //   },
  //   legend: { position: "right" },
  //   scales: { xAxes: [{}], yAxes: [{}] },
  // };
  // public barChartLabels: Label[] = [];
  // public barChartType: ChartType = 'bar';
  // public barChartLegend = true;

  // public barChartData: ChartDataSets[] = [];

  error: string;

  patientsPerAllergy: Array<PatientsPerAllergy>;
  patientsPerDiet: Array<PatientsPerDiet>;
  patientsByStatus: PatientsByStatus;


  // public barChartOptions2: ChartOptions = {
  //   responsive: true,
  //   title: {
  //     display: true,
  //     fontSize: 13,
  //     text: "Composition nutritionnelle (100g)",
  //   },
  //   legend: { position: "right" },
  //   scales: { xAxes: [{}], yAxes: [{}] },
  // };
  // public barChartLabels2: Label[] = [];
  // public barChartType2: ChartType = 'bar';
  // public barChartLegend2 = true;

  // public barChartData2: ChartDataSets[] = [
  // ];

  constructor(
    private statisticsService: StatisticsService) { }

  ngOnInit(): void {
    this.getPatientsPerAllergy();
    this.getPatientsPerDiet();
    this.getPatientsByStatus();
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
