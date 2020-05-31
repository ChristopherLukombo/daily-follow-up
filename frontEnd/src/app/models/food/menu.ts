import { Diet } from "../patient/diet";
import { Week } from "./week";
import { Content } from "./content";

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
  replacements: Array<Content>;
  /***/
  startDate: string;
  /***/
  texture: string;
  /***/
  weeks: Array<Week>;
}
