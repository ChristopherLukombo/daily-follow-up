import { Component, OnInit } from "@angular/core";
import { Floor } from "src/app/models/clinic/floor";
import { ClinicService } from "src/app/services/clinic/clinic.service";
import { TypeMessage } from "src/app/models/utils/message-enum";

/**
 * @author neal
 * @version 17
 */
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
      return TypeMessage.NOT_AUTHENTICATED;
    } else if (error && error === 403) {
      return TypeMessage.NOT_AUTHORIZED;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
