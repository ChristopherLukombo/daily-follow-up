import { Component, OnInit, Input } from '@angular/core';
import { Label, SingleDataSet } from 'ng2-charts';
import { ChartType } from 'chart.js';
import { Content } from '@angular/compiler/src/render3/r3_ast';
import { PatientsByStatus } from 'src/app/models/statistics/patients-by-status';

@Component({
  selector: 'app-patients-by-status',
  templateUrl: './patients-by-status.component.html',
  styleUrls: ['./patients-by-status.component.scss']
})
export class PatientsByStatusComponent implements OnInit {

  doughnutChartLabels: Label[] = [
    "actives",
    "inactives"
  ];
  doughnutChartoptions = {
    title: {
      display: true,
      fontSize: 13,
      text: "Composition nutritionnelle (100g)",
    },
    legend: { position: "right" },
  };
  doughnutChartData: SingleDataSet = new Array<number>(2);
  doughnutChartType: ChartType = "doughnut";

  @Input() patientsByStatus: PatientsByStatus

  constructor() {}

  ngOnInit(): void {
  }

  ngOnChanges(): void {
    debugger
    this.doughnutChartData = this.patientsByStatus
      ? this.generateChartData(this.patientsByStatus)
      : [0, 0];
  }



  // generateChartData(data: Content): number[] {
  //   let set: number[] = [];
  //   set[0] = data.protein;
  //   set[1] = data.carbohydrate;
  //   set[2] = data.lipids;
  //   set[3] = data.sugars;
  //   set[4] = data.foodFibres;
  //   set[5] = data.agSaturates;
  //   set[6] = data.salt;
  //   return set;
  // }


  generateChartData(data: PatientsByStatus) {
    debugger
    const activePatients = data.activePatients;
    const inactivePatients = data.inactivePatients;
    // const di = data.map(d => d.totalPatients)

    // this.barChartLabels = data.map(d => d.dietName);
    return [activePatients, inactivePatients]

    // return [
    //   { data: di, label: 'Nombre de patients' },
    //   { data: data.map(d => d.percentage), label: 'Patients en pourcentage' }
    // ];
  }


}
