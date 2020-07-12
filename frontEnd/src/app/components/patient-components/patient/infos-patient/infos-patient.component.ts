import { Component, OnInit, Input } from "@angular/core";
import { faBed } from "@fortawesome/free-solid-svg-icons";
import { Patient } from "src/app/models/patient/patient";
import { Room } from "src/app/models/clinic/room";
import { ClinicService } from "src/app/services/clinic/clinic.service";
import { TypeMessage } from "src/app/models/utils/message-enum";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-infos-patient",
  templateUrl: "./infos-patient.component.html",
  styleUrls: ["./infos-patient.component.scss"],
})
export class InfosPatientComponent implements OnInit {
  roomLogo = faBed;

  @Input() patient: Patient;
  room: Room;

  warning: string;

  constructor(private clinicService: ClinicService) {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    this.room = null;
    if (this.patient && this.patient.roomId && this.patient.state == true) {
      this.clinicService.getRoom(this.patient.roomId).subscribe(
        (data) => {
          this.room = data;
        },
        (error) => {
          this.warning = TypeMessage.INFORMATIONS_OF_ROOMS_NOT_AVAILABLE;
        }
      );
    }
  }

  changesRoom(room: Room): void {
    this.room = room;
  }
}
