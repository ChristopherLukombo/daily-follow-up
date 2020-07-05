import { Component, OnInit } from "@angular/core";
import { OrdersPerDay } from "src/app/models/statistics/orders-per-day";
import { TopTrendyMenu } from "src/app/models/statistics/top-trendy-menu";
import { StatisticsService } from "src/app/services/statistics/statistics.service";

@Component({
  selector: "app-statistics-food",
  templateUrl: "./statistics-food.component.html",
  styleUrls: ["./statistics-food.component.scss"],
})
export class StatisticsFoodComponent implements OnInit {
  error: string;

  orderPerStatus: Map<string, Array<OrdersPerDay>>;
  topTrendyMenus: Array<TopTrendyMenu>;
  topTrendyContents: Map<string, number>;

  constructor(private statisticsService: StatisticsService) {}

  ngOnInit(): void {
    this.getNumberOfOrdersPerDay();
    this.getTrendyDiets();
    this.getTrendyContents();
  }

  private getNumberOfOrdersPerDay(): void {
    this.statisticsService.getNumberOfOrdersPerDay().subscribe(
      (data) => {
        this.orderPerStatus = data;
      },
      (error) => {
        this.catchError(error);
      }
    );
  }

  private getTrendyDiets(): void {
    this.statisticsService.getTrendyDiets().subscribe(
      (data) => {
        this.topTrendyMenus = data;
      },
      (error) => {
        this.catchError(error);
      }
    );
  }

  private getTrendyContents(): void {
    this.statisticsService.getTrendyContents().subscribe(
      (data) => {
        this.topTrendyContents = data;
      },
      (error) => {
        this.catchError(error);
      }
    );
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  catchError(error: number): void {
    if (error && error === 401) {
      this.error =
        "Le nom d'utilisateur et le mot de passe ne correspondent pas.";
    } else {
      this.error = "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
  /**
   * Suppression des msg d'erreurs
   */
  cleanErrorMessages(): void {
    this.error = undefined;
  }
}
