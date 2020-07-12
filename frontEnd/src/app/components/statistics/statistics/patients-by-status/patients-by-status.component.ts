import { Component, Input, OnInit } from "@angular/core";
import { ChartType } from "chart.js";
import { Label, SingleDataSet } from "ng2-charts";
import { PatientsByStatus } from "src/app/models/statistics/patients-by-status";

/**
 * @author christopher
 * @version 17
 */
@Component({
  selector: "app-patients-by-status",
  templateUrl: "./patients-by-status.component.html",
  styleUrls: ["./patients-by-status.component.scss"],
})
export class PatientsByStatusComponent implements OnInit {
  doughnutChartLabels: Label[] = [
    "Présents dans la clinique",
    "Partis de la clinique",
  ];
  doughnutChartoptions = {
    title: {
      display: true,
      fontSize: 13,
      text: "Entrées et les départs",
    },
    legend: { position: "right" },
  };
  doughnutChartData: SingleDataSet = new Array<number>(2);
  doughnutChartType: ChartType = "doughnut";

  @Input() patientsByStatus: PatientsByStatus;

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    this.doughnutChartData = this.patientsByStatus
      ? this.generateChartData(this.patientsByStatus)
      : [0, 0];
  }

  generateChartData(data: PatientsByStatus) {
    const activePatients: number = data.activePatients;
    const inactivePatients: number = data.inactivePatients;
    return [activePatients, inactivePatients];
  }
}
