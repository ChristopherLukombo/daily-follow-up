import { Diet } from "../patient/diet";
import { Texture } from "./texture";
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
  replacements: Content[];
  /***/
  startDate: string;
  /***/
  texture: Texture;
  /***/
  weeks: Week[];
  /***/
  lastModifiedBy: string;
  /***/
  lastModificationDateBy: Date;
}
