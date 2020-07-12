import { Component, OnInit, Input, ChangeDetectorRef } from "@angular/core";
import { Caregiver } from "src/app/models/user/caregiver";
import { Floor } from "src/app/models/clinic/floor";
import { faInfoCircle } from "@fortawesome/free-solid-svg-icons";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-list-caregivers",
  templateUrl: "./list-caregivers.component.html",
  styleUrls: ["./list-caregivers.component.scss"],
})
export class ListCaregiversComponent implements OnInit {
  detailLogo = faInfoCircle;
  page: number = 1;
  pagination = { itemsPerPage: 8, currentPage: this.page };

  lastName: string;
  firstName: string;

  selectedCaregiver: Caregiver;

  @Input() caregivers: Caregiver[] = [];
  @Input() floors: Floor[] = [];

  filteredCaregivers: Caregiver[] = [];
  filter: boolean[] = [];

  constructor(private cd: ChangeDetectorRef) {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.caregivers) this.filteredCaregivers = this.caregivers;
    if (this.floors) {
      this.filter = [];
      this.floors.forEach((f) => {
        this.filter.push(true);
      });
    }
  }

  filterChange(): void {
    this.filteredCaregivers = this.caregivers.filter((c) => {
      for (let i = 0; i <= this.filter.length; i++) {
        if (this.filter[i] === true) return c.floorId === this.floors[i].id;
      }
    });
  }

  pageChanged(event) {
    this.pagination.currentPage = event;
  }

  selectCaregiver(caregiver: Caregiver): void {
    this.selectedCaregiver = caregiver;
    // force NgOnChanges
    this.cd.detectChanges();
  }

  removeFromList(id: number): void {
    this.filteredCaregivers = this.filteredCaregivers.filter(
      (c) => c.id !== id
    );
  }
}
