import { Component, OnInit, Input } from "@angular/core";
import { faClock, faMarker, faPlus } from "@fortawesome/free-solid-svg-icons";
import { HistoryPatient } from "src/app/models/history/history-patient";

@Component({
  selector: "app-activity-patient",
  templateUrl: "./activity-patient.component.html",
  styleUrls: ["./activity-patient.component.scss"],
})
export class ActivityPatientComponent implements OnInit {
  circleLogo = faClock;
  editLogo = faMarker;
  moreLogo = faPlus;

  @Input() histories: HistoryPatient[] = [];
  activities: Map<string, Array<HistoryPatient>> = new Map<
    string,
    Array<HistoryPatient>
  >();

  constructor() {}

  ngOnInit(): void {}

  /**
   * Obligatoire pour pas que la pipe
   * keyvalue casse l'ordre des activités par date
   */
  dateAsc = () => {
    return 0;
  };

  ngOnChanges(): void {
    if (this.histories) {
      this.activities = this.buildActivitiesByDate();
    }
  }

  /**
   * Construit les activités par date
   * @return la Map clé=date, valeur=historique[]
   */
  buildActivitiesByDate(): Map<string, Array<HistoryPatient>> {
    let result = new Map<string, Array<HistoryPatient>>();
    this.histories.forEach((a) => {
      let key: string = new Date(a.modifiedDate).toLocaleDateString();
      let value: HistoryPatient[] = [];
      if (result.has(key)) {
        value = result.get(key);
      }
      value.push(a);
      result.set(key, value);
    });
    return result;
  }
}
