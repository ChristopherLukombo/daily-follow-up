import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import { ClinicService } from "src/app/services/clinic/clinic.service";
import { Room } from "src/app/models/clinic/room";

@Component({
  selector: "app-room-available-selector",
  templateUrl: "./room-available-selector.component.html",
  styleUrls: ["./room-available-selector.component.scss"],
})
export class RoomAvailableSelectorComponent implements OnInit {
  rooms: Room[] = [];

  filter: string;

  page: number = 1;
  pagination = { itemsPerPage: 8, currentPage: this.page };

  @Output() selectedRoom = new EventEmitter<Room>();
  selectedRow: number;

  constructor(private clinicService: ClinicService) {}

  ngOnInit(): void {
    this.clinicService.getAllAvailableRooms().subscribe(
      (data) => {
        this.rooms = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  pageChanged(event) {
    this.pagination.currentPage = event;
  }

  selectRoom(room: Room, index: number): void {
    this.selectedRow = index;
    this.selectedRoom.emit(room);
  }
}
