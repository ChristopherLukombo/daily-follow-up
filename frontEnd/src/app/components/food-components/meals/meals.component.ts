import { Component, OnInit } from "@angular/core";
import { Content } from "src/app/models/food/content";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";

@Component({
  selector: "app-meals",
  templateUrl: "./meals.component.html",
  styleUrls: ["./meals.component.scss"],
})
export class MealsComponent implements OnInit {
  contents: Content[] = [];

  error: string;
  loading: Boolean = false;

  constructor(private alimentationService: AlimentationService) {}

  ngOnInit(): void {
    this.loading = true;
    this.alimentationService.getAllContents().subscribe(
      (data) => {
        this.contents = data;
      },
      (error) => {
        this.catchError(error);
      },
      () => {
        this.loading = false;
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
        "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else {
      this.error = "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
