import { Component, OnInit, Input } from "@angular/core";
import { Caregiver } from "src/app/models/user/caregiver";
import { Floor } from "src/app/models/clinic/floor";
import { User } from "src/app/models/user/user";

@Component({
  selector: "app-list-caregivers",
  templateUrl: "./list-caregivers.component.html",
  styleUrls: ["./list-caregivers.component.scss"],
})
export class ListCaregiversComponent implements OnInit {
  page: number = 1;
  pagination = { itemsPerPage: 8, currentPage: this.page };

  lastName: string;
  firstName: string;

  selectedRow: number;
  selectedCaregiver: Caregiver;

  @Input() caregivers: Caregiver[] = [];
  @Input() floors: Floor[] = [];

  constructor() {}

  ngOnInit(): void {}

  pageChanged(event) {
    this.pagination.currentPage = event;
  }

  selectCaregiver(caregiver: Caregiver, index: number): void {
    this.selectedRow = index;
    this.selectedCaregiver = caregiver;
  }
}
