import { Component, OnInit, Input } from "@angular/core";
import { Floor } from "src/app/models/clinic/floor";
import { UserService } from "src/app/services/user/user.service";
import { PatientService } from "src/app/services/patient/patient.service";
import { Caregiver } from "src/app/models/user/caregiver";
import { forkJoin } from "rxjs";
import { TypeMessage } from "src/app/models/utils/message-enum";

@Component({
  selector: "app-infos-floor",
  templateUrl: "./infos-floor.component.html",
  styleUrls: ["./infos-floor.component.scss"],
})
export class InfosFloorComponent implements OnInit {
  @Input() floor: Floor;

  caregivers: Caregiver[] = [];
  freePlaces: number = 0;
  patientsLength: number = 0;

  loading: boolean = false;
  error: string;

  constructor(
    private userService: UserService,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.floor) {
      this.loading = true;
      let patientsOfTheFloor = this.patientService.getPatientsByFloor(
        this.floor.number
      );
      let caregiversOfTheFloor = this.userService.getCaregiversByFloor(
        this.floor.number
      );
      forkJoin([patientsOfTheFloor, caregiversOfTheFloor]).subscribe(
        (datas) => {
          this.patientsLength = datas[0].length;
          this.caregivers = datas[1];
          this.setFreePlacesOfTheFloor(this.floor);
          this.loading = false;
        },
        (error) => {
          this.error = this.getError(error);
          this.loading = false;
        }
      );
    }
  }

  setFreePlacesOfTheFloor(floor: Floor): void {
    floor.rooms.forEach((room) => {
      this.freePlaces += room.maxCapacity - room.numberOfPatients;
    });
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getError(error: number): string {
    if (error && error === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
