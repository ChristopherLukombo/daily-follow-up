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

  @Input() isCaregiver: boolean = false;

  constructor(private cd: ChangeDetectorRef) {}

  ngOnInit(): void {}

  ngOnChanges(): void {}

  pageChanged(event) {
    this.pagination.currentPage = event;
  }

  selectCaregiver(caregiver: Caregiver): void {
    this.selectedCaregiver = caregiver;
    // force NgOnChanges
    this.cd.detectChanges();
  }

  removeFromList(id: number): void {
    this.caregivers = this.caregivers.filter((c) => c.id !== id);
  }
}
