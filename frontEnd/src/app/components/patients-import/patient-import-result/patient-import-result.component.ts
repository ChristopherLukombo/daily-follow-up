import { Component, OnInit, Input } from "@angular/core";
import { ResultCsvPatient } from "src/app/models/csv/result-csv-patient";

@Component({
  selector: "app-patient-import-result",
  templateUrl: "./patient-import-result.component.html",
  styleUrls: ["./patient-import-result.component.scss"],
})
export class PatientImportResultComponent implements OnInit {
  @Input() result: ResultCsvPatient;

  constructor() {}

  ngOnInit(): void {}
}
