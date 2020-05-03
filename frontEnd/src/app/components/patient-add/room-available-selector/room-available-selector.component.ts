import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import { ClinicService } from "src/app/services/clinic/clinic.service";
import { Room } from "src/app/models/clinic/room";
import { Floor } from "src/app/models/clinic/floor";
import { faCheckCircle } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-room-available-selector",
  templateUrl: "./room-available-selector.component.html",
  styleUrls: ["./room-available-selector.component.scss"],
})
export class RoomAvailableSelectorComponent implements OnInit {
  roomLogo = faCheckCircle;

  floors: Floor[] = [];
  room: Room;
  input: Room;
  @Output() selectedRoom = new EventEmitter<Room>();

  constructor(private clinicService: ClinicService) {}

  ngOnInit(): void {
    this.clinicService.getAllFloors().subscribe(
      (data) => {
        this.floors = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  selectRoom(room: Room): void {
    this.room = room;
  }

  confirm(): void {
    this.input = this.room;
    if (!this.input) return;
    this.selectedRoom.emit(this.input);
  }
}
