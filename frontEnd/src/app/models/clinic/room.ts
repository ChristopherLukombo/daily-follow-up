import { Patient } from "../patient/patient";

export class Room {
  /***/
  id: number;
  /***/
  number: string;
  /***/
  state: Boolean;
  /***/
  patients: Array<Patient>;
}
