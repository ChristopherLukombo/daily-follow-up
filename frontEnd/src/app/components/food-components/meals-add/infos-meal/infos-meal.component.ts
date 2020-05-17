import { Component, OnInit } from "@angular/core";
import { ChartType } from "chart.js";
import { Label, SingleDataSet } from "ng2-charts";

@Component({
  selector: "app-infos-meal",
  templateUrl: "./infos-meal.component.html",
  styleUrls: ["./infos-meal.component.scss"],
})
export class InfosMealComponent implements OnInit {
  doughnutChartLabels: Label[] = [
    "Protéines",
    "Glucides",
    "Lipides",
    "Sucres",
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
  doughnutChartData: SingleDataSet = [9.3, 2.5, 6.4, 2, 1.7, 0.89];
  doughnutChartType: ChartType = "doughnut";

  constructor() {}

  ngOnInit(): void {}
}
