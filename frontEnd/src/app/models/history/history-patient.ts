import { Patient } from "../patient/patient";

export class HistoryPatient {
  /***/
  action: string;
  /***/
  id: number;
  /***/
  modifiedBy: string;
  /***/
  modifiedDate: Date;
  /***/
  patient: Patient;
}
