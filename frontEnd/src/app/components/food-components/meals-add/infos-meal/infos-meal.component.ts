import { Component, OnInit, Input } from "@angular/core";
import { ChartType } from "chart.js";
import { Label, SingleDataSet } from "ng2-charts";
import { Content } from "src/app/models/food/content";
import { FormGroup } from "@angular/forms";

@Component({
  selector: "app-infos-meal",
  templateUrl: "./infos-meal.component.html",
  styleUrls: ["./infos-meal.component.scss"],
})
export class InfosMealComponent implements OnInit {
  illegalInputFloat: string[] = ["e", "-", "+", "*"];
  noContent: string =
    "Il n'éxiste aucune information connue sur ce plat. Veuillez renseigner les ingédients du plat pour le créer.";

  @Input() content: Content = null;
  @Input() form: FormGroup = null;
  @Input() submitted: boolean = false;

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
  doughnutChartData: SingleDataSet = new Array<number>(6);
  doughnutChartType: ChartType = "doughnut";

  constructor() {}

  ngOnInit(): void {
    if (this.content && this.form) {
      this.f.calories.setValue(this.content.calories);
      this.f.protein.setValue(this.content.protein);
      this.f.carbohydrate.setValue(this.content.carbohydrate);
      this.f.lipids.setValue(this.content.lipids);
      this.f.sugars.setValue(this.content.sugars);
      this.f.agSaturates.setValue(this.content.agSaturates);
      this.f.salt.setValue(this.content.salt);
    }
  }

  ngOnChanges(): void {
    this.doughnutChartData = this.content
      ? this.generateChartData(this.content)
      : [0, 0, 0, 0, 0, 0];
  }

  get f() {
    return this.form.controls;
  }

  generateChartData(data: Content): number[] {
    let set: number[] = [];
    set[0] = data.protein;
    set[1] = data.carbohydrate;
    set[2] = data.lipids;
    set[3] = data.sugars;
    set[4] = data.agSaturates;
    set[5] = data.salt;
    return set;
  }

  chartEmpty(): boolean {
    const set: number[] = this.doughnutChartData as number[];
    return set.filter((data: number) => !data).length == 6;
  }

  updateValues(event: KeyboardEvent, key: string, index?: number): void {
    if (this.illegalInputFloat.indexOf(event.key) !== -1) {
      event.preventDefault();
    }
    this.updateChart(key, index);
  }

  updateChart(key: string, index?: number): void {
    if (index !== undefined && key !== "calories") {
      this.doughnutChartData[index] = this.f[key].value;
      this.doughnutChartData = this.doughnutChartData.slice();
    }
  }
}
