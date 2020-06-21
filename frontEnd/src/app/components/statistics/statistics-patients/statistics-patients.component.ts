import { Component, OnInit } from '@angular/core';
import { Label, SingleDataSet } from 'ng2-charts';
import { ChartType } from 'chart.js';
import { Content } from 'src/app/models/food/content';

@Component({
  selector: 'app-statistics-patients',
  templateUrl: './statistics-patients.component.html',
  styleUrls: ['./statistics-patients.component.scss']
})
export class StatisticsPatientsComponent implements OnInit {

  doughnutChartLabels: Label[] = [
    "Protéines",
    "Glucides",
    "Lipides",
    "Sucres",
    "Fibres",
    "AG saturés",
    "Sel chlorire de sodium",
  ];
  doughnutChartoptions = {
    title: {
      display: true,
      fontSize: 13,
      text: "Composition nutritionnelle (100g)",
    },
    legend: { position: "right" },
  };
  doughnutChartData: SingleDataSet = new Array<number>(7);
  doughnutChartType: ChartType = "doughnut";

  constructor() { }

  ngOnInit(): void {
  }

  // ngOnChanges(): void {
  //   this.doughnutChartData = this.content
  //     ? this.generateChartData(this.content)
  //     : [0, 0, 0, 0, 0, 0, 0];
  // }

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

}
