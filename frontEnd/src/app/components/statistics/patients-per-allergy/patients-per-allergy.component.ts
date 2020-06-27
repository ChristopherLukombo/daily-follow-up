import { Component, Input } from '@angular/core';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import { Label } from 'ng2-charts';
import { PatientsPerAllergy } from 'src/app/models/statistics/patients-per-allergy';

@Component({
  selector: 'app-patients-per-allergy',
  templateUrl: './patients-per-allergy.component.html',
  styleUrls: ['./patients-per-allergy.component.scss']
})
export class PatientsPerAllergyComponent {

 public barChartOptions: ChartOptions = {
    responsive: true,
    title: {
      display: true,
      fontSize: 13,
      text: "Composition nutritionnelle (100g)",
    },
    legend: { position: "right" },
    scales: { xAxes: [{}], yAxes: [{}] },
  };
public barChartLabels: Label[] = [];
   public barChartType: ChartType = 'bar';
   public barChartLegend = true;

public barChartData: ChartDataSets[] = [];

  @Input() public patientsPerAllergy: Array<PatientsPerAllergy>


  constructor() { }

  ngOnChanges(): void {
    this.barChartData = this.patientsPerAllergy ? this.generateChartData(this.patientsPerAllergy) :[ { data: [0, 0, 0, 0, 0, 0, 0], label: '' }];
  }

  generateChartData(data: Array<PatientsPerAllergy>): { data: number[]; label: string; }[] {
    const di = data.map(d => d.numberPatients)

   this.barChartLabels = data.map(d => d.allergyName);

    return [
      {data: di, label: 'Nombre de patients'},
      {data: data.map(d => d.percentage), label: 'Patients en pourcentage'}
    ];
  }

}
