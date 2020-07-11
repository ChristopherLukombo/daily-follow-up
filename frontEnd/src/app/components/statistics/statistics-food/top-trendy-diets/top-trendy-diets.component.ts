import { Component, Input, OnInit } from "@angular/core";
import { ChartType } from "chart.js";
import { Label, SingleDataSet } from "ng2-charts";
import { TopTrendyMenu } from "src/app/models/statistics/top-trendy-menu";

@Component({
  selector: "app-top-trendy-diets",
  templateUrl: "./top-trendy-diets.component.html",
  styleUrls: ["./top-trendy-diets.component.scss"],
})
export class TopTrendyDietsComponent implements OnInit {
  @Input() public topTrendyMenus: Array<TopTrendyMenu>;

  doughnutChartLabels: Label[] = [];
  doughnutChartoptions = {
    title: {
      display: true,
      fontSize: 13,
      text: "Les 5 régimes les plus présents (Menu)",
    },
    legend: { position: "right" },
  };
  doughnutChartData: SingleDataSet = new Array<number>(2);
  doughnutChartType: ChartType = "pie";

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    this.doughnutChartData = this.topTrendyMenus
      ? this.generateChartData(this.topTrendyMenus)
      : [0, 0, 0, 0, 0];
  }

  generateChartData(data: Array<TopTrendyMenu>): SingleDataSet {
    const labels: Label[] = [];
    for (let i = 0; i < data.length; i++) {
      labels.push(`${data[i].texture} - ${data[i].diets}`);
    }
    this.doughnutChartLabels = labels;
    return data.map((d) => d.nb);
  }
}
