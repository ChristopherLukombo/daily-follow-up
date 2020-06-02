import { Diet } from "../patient/diet";
import { Week } from "./week";
import { Replacement } from "./replacement";

export class Menu {
  /***/
  diet: Diet;
  /***/
  endDate: string;
  /***/
  id: number;
  /***/
  lastModificationDateBy: Date;
  /***/
  lastModifiedBy: string;
  /***/
  replacement: Replacement;
  /***/
  startDate: string;
  /***/
  texture: string;
  /***/
  weeks: Array<Week>;
}
