import { Component, OnInit } from "@angular/core";
import { ClinicService } from "src/app/services/clinic/clinic.service";
import { Floor } from "src/app/models/clinic/floor";

@Component({
  selector: "app-floor-add",
  templateUrl: "./floor-add.component.html",
  styleUrls: ["./floor-add.component.scss"],
})
export class FloorAddComponent implements OnInit {
  colors: Map<number, string> = new Map<number, string>([
    [0, "lightskyblue"],
    [1, "khaki"],
    [2, "pink"],
    [3, "lightblue"],
    [4, "plum"],
    [5, "lightgreen"],
  ]);

  floors: Floor[] = [];
  selectedFloor: Floor;

  newFloorNumber: number;
  roomsToCreate: number;

  loading: boolean = false;
  error: string;
  roomForms: boolean = false;

  constructor(private clinicService: ClinicService) {}

  ngOnInit(): void {
    this.loading = true;
    this.clinicService.getAllFloors().subscribe(
      (data) => {
        this.floors = data;
        this.loading = false;
        this.selectFloor(this.floors[0]);
        this.newFloorNumber = this.floors.length;
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

  getColor(floor: number): string {
    return floor > this.colors.size
      ? this.colors.get(0)
      : this.colors.get(floor);
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
