import { Component, OnInit, Input } from "@angular/core";
import { Patient } from "src/app/models/patient/patient";

@Component({
  selector: "app-food-patient",
  templateUrl: "./food-patient.component.html",
  styleUrls: ["./food-patient.component.scss"],
})
export class FoodPatientComponent implements OnInit {
  @Input() patient: Patient;
  isActive: boolean;

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.patient) {
      this.isActive = this.patient.state;
    }
  }
}
