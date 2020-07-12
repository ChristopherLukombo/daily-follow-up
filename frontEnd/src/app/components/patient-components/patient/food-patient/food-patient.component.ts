import { Component, OnInit, Input } from "@angular/core";
import { Patient } from "src/app/models/patient/patient";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-food-patient",
  templateUrl: "./food-patient.component.html",
  styleUrls: ["./food-patient.component.scss"],
})
export class FoodPatientComponent implements OnInit {
  @Input() patient: Patient;
  isActive: boolean;

  @Input() isCaregiver: boolean = false;

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.patient && this.patient.state && !this.isCaregiver) {
      this.isActive = true;
    }
  }
}
