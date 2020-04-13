import { Component, OnInit, Input } from "@angular/core";
import { faBed } from "@fortawesome/free-solid-svg-icons";
import { Patient } from "src/app/models/patient/patient";
import { Room } from "src/app/models/clinic/room";
import { ClinicService } from "src/app/services/clinic/clinic.service";

@Component({
  selector: "app-infos-patient",
  templateUrl: "./infos-patient.component.html",
  styleUrls: ["./infos-patient.component.scss"],
})
export class InfosPatientComponent implements OnInit {
  roomLogo = faBed;

  @Input() patient: Patient;
  room: Room;

  constructor(private clinicService: ClinicService) {}

  ngOnInit(): void {
    this.clinicService.getRoom(this.patient.roomId).subscribe(
      (data) => {
        this.room = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
