import { Component, Input, OnInit } from '@angular/core';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import * as moment from 'moment';
import { Label } from 'ng2-charts';
import { OrdersPerDay } from 'src/app/models/statistics/orders-per-day';

export function titleCaseWord(word: string) {
  if (!word) return word;
  return word[0].toUpperCase() + word.substr(1).toLowerCase();
}

@Component({
  selector: 'app-order-per-day',
  templateUrl: './order-per-day.component.html',
  styleUrls: ['./order-per-day.component.scss']
})
export class OrderPerDayComponent implements OnInit {

  @Input() orderPerStatus: Map<string, Array<OrdersPerDay>>;

  public barChartOptions: ChartOptions = {
    responsive: true,
    title: {
      display: true,
      fontSize: 13,
      text: "Nombre de commandes par jour (sur la semaine)",
    },
    legend: { position: "right" },
    scales: { xAxes: [{}], yAxes: [{}] },
  };
  public barChartLabels: Label[] = [];
  public barChartType: ChartType = 'bar';
  public barChartLegend = true;

  public barChartData: ChartDataSets[] = [];

  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(): void {
    this.barChartData = this.orderPerStatus ? this.generateChartData(this.orderPerStatus) : [{ data: [0, 0, 0, 0, 0, 0, 0], label: '' }];
  }

  generateChartData(data: Map<string, Array<OrdersPerDay>>): ChartDataSets[] {
    const barChartData: ChartDataSets[] = [];

    for (let entry of data.entries()) {
      const counts = [];
      const labels = [];

      entry[1].forEach(function (key) {
        const day = moment(new Date(key.date)).lang("fr");
        labels.push(`${titleCaseWord(day.format('dddd'))} ${day.format('D')}`);
        counts.push(key.count);
      });

      this.barChartLabels = labels;
      barChartData.push({ data: counts, label: this.mapStatus(entry[0]) });
    }

    return barChartData;
  }

  private mapStatus(orderStatus: string): string {
    if ('WAITING' === orderStatus) {
      return 'En attente';
    } else if ('CANCEL' === orderStatus) {
      return 'Annulée';
    } else if ('VALIDATED' === orderStatus) {
      return 'Validé';
    }
  }
}
