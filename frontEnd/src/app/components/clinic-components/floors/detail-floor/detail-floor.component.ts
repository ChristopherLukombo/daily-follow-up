import { Component, OnInit, Input } from "@angular/core";
import { Floor } from "src/app/models/clinic/floor";
import { Patient } from "src/app/models/patient/patient";
import { Caregiver } from "src/app/models/user/caregiver";
import { UserService } from "src/app/services/user/user.service";
import { PatientService } from "src/app/services/patient/patient.service";
import { forkJoin } from "rxjs";
import { Room } from "src/app/models/clinic/room";
import { faDoorOpen } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-detail-floor",
  templateUrl: "./detail-floor.component.html",
  styleUrls: ["./detail-floor.component.scss"],
})
export class DetailFloorComponent implements OnInit {
  roomLogo = faDoorOpen;

  @Input() floor: Floor;
  roomsOfPatients: Map<Room, Patient[]> = new Map<Room, Patient[]>();
  freePlaces: number = 0;
  patients: Patient[] = [];
  caregivers: Caregiver[] = [];

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
          this.patients = datas[0];
          this.caregivers = datas[1];
          this.loadRooms(this.floor, this.patients);
          this.loading = false;
        },
        (error) => {
          this.error = this.getError(error);
          this.loading = false;
        }
      );
    }
  }

  loadRooms(floor: Floor, patients: Patient[]): void {
    this.roomsOfPatients.clear();
    this.freePlaces = 0;
    floor.rooms.forEach((room) => {
      this.freePlaces += room.maxCapacity - room.numberOfPatients;
      this.roomsOfPatients.set(
        room,
        patients.filter((p) => p.roomId === room.id)
      );
    });
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getError(error: number): string {
    if (error && error === 401) {
      return "Vous n'êtes plus connecté, veuillez rafraichir le navigateur.";
    } else {
      return "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
