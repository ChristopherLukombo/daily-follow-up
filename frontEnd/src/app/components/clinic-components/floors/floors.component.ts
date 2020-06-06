import { Component, OnInit } from "@angular/core";
import { Floor } from "src/app/models/clinic/floor";
import { ClinicService } from "src/app/services/clinic/clinic.service";

@Component({
  selector: "app-floors",
  templateUrl: "./floors.component.html",
  styleUrls: ["./floors.component.scss"],
})
export class FloorsComponent implements OnInit {
  floors: Floor[] = [];
  selectedFloor: Floor;

  loading: boolean = false;
  error: string;

  constructor(private clinicService: ClinicService) {}

  ngOnInit(): void {
    this.loading = true;
    this.clinicService.getAllFloors().subscribe(
      (data) => {
        this.floors = data;
        this.loading = false;
        this.selectFloor(this.floors[0]);
      },
      (error) => {
        this.error = this.getError(error);
        this.loading = false;
      }
    );
  }

  selectFloor(floor: Floor): void {
    this.selectedFloor = floor;
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  getError(error: number): string {
    if (error && error === 401) {
      return "Vous n'êtes plus connecté, veuillez rafraichir le navigateur.";
    } else if (error && error === 403) {
      return "Vous n'êtes plus habilité à cette requête.";
    } else {
      return "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
