import { Component, OnInit, Output, EventEmitter, Input } from "@angular/core";
import { Floor } from "src/app/models/clinic/floor";
import { ClinicService } from "src/app/services/clinic/clinic.service";
import { Room } from "src/app/models/clinic/room";

@Component({
  selector: "app-list-rooms-available",
  templateUrl: "./list-rooms-available.component.html",
  styleUrls: ["./list-rooms-available.component.scss"],
})
export class ListRoomsAvailableComponent implements OnInit {
  @Input() floors: Floor[] = [];

  selectedButton: number = 0;
  floor: Floor;

  filter: string;

  page: number = 1;
  pagination = { itemsPerPage: 6, currentPage: this.page };

  @Output() selectedRoom = new EventEmitter<Room>();
  selectedRow: number;

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.floors) {
      this.floors = this.getRoomsAvailable(this.floors);
      this.selectFloor(this.floors[0], 0);
    }
  }

  getRoomsAvailable(floors: Floor[]): Floor[] {
    floors.forEach((f) =>
      f.rooms.forEach((r) => {
        if (r.isFull === true) {
          const index = f.rooms.indexOf(r);
          f.rooms.splice(index, 1);
        }
      })
    );
    return floors.sort((a, b) => a.number - b.number);
  }

  selectFloor(floor: Floor, index: number): void {
    this.selectedButton = index;
    this.floor = floor;
  }

  pageChanged(event) {
    this.pagination.currentPage = event;
  }

  selectRoom(room: Room, index: number): void {
    this.selectedRow = index;
    this.selectedRoom.emit(room);
  }
}
