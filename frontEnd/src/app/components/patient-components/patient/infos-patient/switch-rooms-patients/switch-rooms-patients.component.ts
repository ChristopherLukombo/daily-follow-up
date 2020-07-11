import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { Room } from "src/app/models/clinic/room";
import { faExchangeAlt } from "@fortawesome/free-solid-svg-icons";
import { ClinicService } from "src/app/services/clinic/clinic.service";
import { PatientService } from "src/app/services/patient/patient.service";
import { Floor } from "src/app/models/clinic/floor";
import { forkJoin } from "rxjs";
import { Patient } from "src/app/models/patient/patient";
import { TypeMessage } from "src/app/models/utils/message-enum";
import { ToastrService } from "ngx-toastr";
import { mergeMap } from "rxjs/operators";

@Component({
  selector: "app-switch-rooms-patients",
  templateUrl: "./switch-rooms-patients.component.html",
  styleUrls: ["./switch-rooms-patients.component.scss"],
})
export class SwitchRoomsPatientsComponent implements OnInit {
  switchLogo = faExchangeAlt;

  @Input() room: Room;
  @Input() patient: Patient;

  floors: Floor[] = [];
  patients: Patient[] = [];

  selectedFloor: Floor;
  patientsOfRooms: Map<string, Room> = new Map<string, Room>();
  selectedPatient: Patient;

  page: number = 1;
  pagination = { itemsPerPage: 6, currentPage: this.page };

  loading: boolean = false;
  error: string;

  exchanging: boolean = false;
  @Output() roomChanges = new EventEmitter<Room>();

  constructor(
    private clinicService: ClinicService,
    private patientService: PatientService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.room) {
      this.loading = true;
      let allFloors = this.clinicService.getAllFloors();
      let allPatients = this.patientService.getAllPatients();
      forkJoin([allFloors, allPatients]).subscribe(
        (datas) => {
          if (datas[0] && datas[1]) {
            this.floors = datas[0];
            this.patients = datas[1];
            this.selectFloor(this.floors[0]);
          }
          this.loading = false;
        },
        (error) => {
          this.error = this.getError(error);
          this.loading = false;
        }
      );
    }
  }

  selectFloor(floor: Floor): void {
    this.selectedFloor = floor;
    this.loadPatientsOfRooms(this.selectedFloor);
  }

  loadPatientsOfRooms(floor: Floor): void {
    this.patientsOfRooms.clear();
    floor.rooms.forEach((room) => {
      let patient: Patient = this.patients.find((p) => p.roomId === room.id);
      if (patient) {
        this.patientsOfRooms.set(
          patient.lastName + " " + patient.firstName,
          room
        );
      }
    });
    this.patientsOfRooms.delete(
      this.patient.lastName + " " + this.patient.firstName
    );
  }

  selectPatient(name: string): void {
    let lastName: string = name.split(" ")[0];
    let firstName: string = name.split(" ")[1];
    this.selectedPatient = this.patients.find(
      (p) => p.lastName === lastName && p.firstName && firstName
    );
  }

  pageChanged(event) {
    this.pagination.currentPage = event;
  }

  onSubmit(): void {
    if (!this.selectedPatient) return;
    this.exchanging = true;
    this.patientService
      .changesRoom(this.patient.id, this.selectedPatient.id)
      .pipe(mergeMap(() => this.clinicService.getRoom(this.selectedPatient.id)))
      .subscribe(
        (data) => {
          this.exchanging = false;
          this.toastrService.success(
            this.patient.lastName +
              " " +
              this.patient.firstName +
              " et " +
              this.selectedPatient.lastName +
              " " +
              this.selectedPatient.firstName +
              " ont bien échangés leurs chambres",
            "Échange terminé !"
          );
          this.selectedPatient = null;
          this.roomChanges.emit(data);
        },
        (error) => {
          this.exchanging = false;
          this.toastrService.error(this.getError(error), "Oops !");
        }
      );
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
