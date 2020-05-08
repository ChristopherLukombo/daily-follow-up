import { PatientDTO } from "./patientDTO";
import { ContentDTO } from "../food/contentDTO";
import { MomentDayDTO } from "../food/moment-dayDTO";

export class OrderDTO {
  /***/
  id: number;
  /***/
  date: Date;
  /***/
  contents: Array<ContentDTO>;
  /***/
  momentDays: Array<MomentDayDTO>;
  /***/
  patient: PatientDTO;

  constructor(
    id: number,
    date: Date,
    contents: Array<ContentDTO>,
    momentDays: Array<MomentDayDTO>,
    patient: PatientDTO
  ) {
    this.id = id;
    this.date = date;
    this.contents = contents;
    this.momentDays = momentDays;
    this.patient = patient;
  }
}
