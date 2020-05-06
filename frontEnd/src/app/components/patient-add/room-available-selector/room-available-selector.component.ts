import { Component, OnInit, Output, EventEmitter, Input } from "@angular/core";
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
  @Input() firstSelected: number = null;
  room: Room;
  input: Room;
  @Output() selectedRoom = new EventEmitter<Room>();

  loading: boolean = false;
  warning: string;

  constructor(private clinicService: ClinicService) {}

  ngOnInit(): void {
    this.loading = true;
    this.clinicService.getAllFloors().subscribe(
      (data) => {
        this.floors = data;
        if (this.firstSelected) {
          this.selectRoom(this.getFirstSelected(this.firstSelected));
          this.confirm();
        }
        this.loading = false;
      },
      (error) => {
        this.warning =
          "Impossible de récupérer les chambres disponibles pour le moment.";
        this.loading = false;
      }
    );
  }

  getFirstSelected(id: number): Room {
    let room: Room;
    for (let i = 0; i < this.floors.length; i++) {
      for (let j = 0; j < this.floors[i].rooms.length; j++) {
        if (this.floors[i].rooms[j].id === id) {
          room = this.floors[i].rooms[j];
        }
      }
    }
    return room;
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
