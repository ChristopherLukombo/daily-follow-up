import { Week } from "./week";
import { Replacement } from "./replacement";

export class Menu {
  /***/
  diets: Array<string>;
  /***/
  endDate: string;
  /***/
  id: number;
  /***/
  lastModificationDateBy: Date;
  /***/
  lastModifiedBy: string;
  /***/
  name: string;
  /***/
  replacement: Replacement;
  /***/
  startDate: string;
  /***/
  texture: string;
  /***/
  weeks: Array<Week>;
}
