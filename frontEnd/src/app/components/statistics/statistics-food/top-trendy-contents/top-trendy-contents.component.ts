import { Component, Input, OnInit } from "@angular/core";
import { ChartType } from "chart.js";
import { Label, SingleDataSet } from "ng2-charts";

/**
 * @author christopher
 * @version 17
 */
@Component({
  selector: "app-top-trendy-contents",
  templateUrl: "./top-trendy-contents.component.html",
  styleUrls: ["./top-trendy-contents.component.scss"],
})
export class TopTrendyContentsComponent implements OnInit {
  @Input() public topTrendyContents: Map<string, number>;

  doughnutChartLabels: Label[] = [];
  doughnutChartoptions = {
    title: {
      display: true,
      fontSize: 13,
      text: "Les 5 plats les plus présents (Menu)",
    },
    legend: { position: "right" },
  };
  doughnutChartData: SingleDataSet = new Array<number>(5);
  doughnutChartType: ChartType = "doughnut";

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    this.doughnutChartData = this.topTrendyContents
      ? this.generateChartData(this.topTrendyContents)
      : [0, 0, 0, 0, 0];
  }

  generateChartData(data: Map<string, number>): SingleDataSet {
    const labels: Label[] = [];
    const values: Array<number> = Array<number>(Object.keys(data).length);

    for (let [key, value] of data) {
      labels.push(key);
      values.push(value);
    }

    this.doughnutChartLabels = labels;
    return values;
  }
}
