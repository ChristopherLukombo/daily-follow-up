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
    "Il n'existe aucune information connue sur ce plat. Veuillez renseigner les ingédients du plat pour le créer.";

  @Input() content: Content = null;
  @Input() form: FormGroup = null;
  @Input() submitted: boolean = false;

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

  constructor() {}

  ngOnInit(): void {
    if (this.form) {
      this.doughnutChartData = this.content
        ? this.generateChartData(this.content)
        : [0, 0, 0, 0, 0, 0, 0];
      setTimeout(() => {
        this.generateForm(this.content);
      });
    }
  }

  get f() {
    return this.form.controls;
  }

  generateForm(data: Content) {
    this.f.calories.setValue(data ? data.calories : null);
    this.f.protein.setValue(data ? data.protein : null);
    this.f.carbohydrate.setValue(data ? data.carbohydrate : null);
    this.f.lipids.setValue(data ? data.lipids : null);
    this.f.sugars.setValue(data ? data.sugars : null);
    this.f.foodFibres.setValue(data ? data.foodFibres : null);
    this.f.agSaturates.setValue(data ? data.agSaturates : null);
    this.f.salt.setValue(data ? data.salt : null);
  }

  generateChartData(data: Content): number[] {
    let set: number[] = [];
    set[0] = data.protein;
    set[1] = data.carbohydrate;
    set[2] = data.lipids;
    set[3] = data.sugars;
    set[4] = data.foodFibres;
    set[5] = data.agSaturates;
    set[6] = data.salt;
    return set;
  }

  chartEmpty(): boolean {
    const set: number[] = this.doughnutChartData as number[];
    return set.filter((data: number) => !data).length == 7;
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
