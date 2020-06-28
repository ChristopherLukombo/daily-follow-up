import { Component, Input } from '@angular/core';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import { Label } from 'ng2-charts';
import { PatientsPerAllergy } from 'src/app/models/statistics/patients-per-allergy';
import { PatientsPerDiet } from 'src/app/models/statistics/patients-per-diet';

@Component({
  selector: 'app-patients-per-diet',
  templateUrl: './patients-per-diet.component.html',
  styleUrls: ['./patients-per-diet.component.scss']
})
export class PatientsPerDietComponent {
  public barChartOptions: ChartOptions = {
    responsive: true,
    title: {
      display: true,
      fontSize: 13,
      text: "Nombre de patients pour chaque alimentation",
    },
    legend: { position: "right" },
    scales: { xAxes: [{}], yAxes: [{}] },
  };
  public barChartLabels: Label[] = [];
  public barChartType: ChartType = 'line';
  public barChartLegend = true;

  public barChartData: ChartDataSets[] = [];

  @Input() public patientsPerDiet: Array<PatientsPerDiet>


  constructor() { }

  ngOnChanges(): void {
    this.barChartData = this.patientsPerDiet ?
      this.generateChartData(this.patientsPerDiet) :
      [{ data: [0, 0, 0, 0, 0, 0, 0], label: '' }];
  }

  generateChartData(data: Array<PatientsPerDiet>): { data: number[]; label: string; }[] {
    this.barChartLabels = data.map(d => d.dietName);

    return [
      { data: data.map(d => d.numberPatients), label: 'Nombre de patients' },
      { data: data.map(d => d.percentage), label: 'Patients en pourcentage' }
    ];
  }
}
