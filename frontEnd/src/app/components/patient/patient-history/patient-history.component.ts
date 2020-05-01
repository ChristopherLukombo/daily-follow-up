import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { HistoryService } from "src/app/services/history/history.service";
import { HistoryPatient } from "src/app/models/history/history-patient";
import { faPlus } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-patient-history",
  templateUrl: "./patient-history.component.html",
  styleUrls: ["./patient-history.component.scss"],
})
export class PatientHistoryComponent implements OnInit {
  moreLogo = faPlus;

  histories: HistoryPatient[] = [];
  size: number = 1;
  totalElements: number;

  error: string;
  warning: string;
  loading: Boolean = false;

  constructor(
    private route: ActivatedRoute,
    private historyService: HistoryService
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.route.queryParams.forEach((params) => {
      this.historyService
        .getAllPatientHistories(parseInt(params["patient"]), this.size)
        .subscribe(
          (data) => {
            this.loading = false;
            if (data) {
              this.histories = data["content"];
              this.totalElements = data["totalElements"];
            } else {
              this.HistoriesNotFound();
            }
          },
          (error) => {
            this.loading = false;
            this.catchError(error);
          }
        );
    });
  }

  HistoriesNotFound(): void {
    this.warning = "Il n'y a aucun historique pour le patient recherché.";
  }

  loadMoreHistories(): void {
    this.size += 5;
    this.route.queryParams.forEach((params) => {
      this.historyService
        .getAllPatientHistories(parseInt(params["patient"]), this.size)
        .subscribe(
          (data) => {
            this.loading = false;
            this.histories = data ? data["content"] : null;
          },
          (error) => {
            this.loading = false;
            this.catchError(error);
          }
        );
    });
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  catchError(error: number): void {
    if (error && error === 401) {
      this.error =
        "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else {
      this.error = "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
