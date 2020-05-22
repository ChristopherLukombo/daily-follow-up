import { Patient } from "./patient";
import { MomentDay } from "../food/moment-day";
import { Content } from "../food/content";

export class Order {
  /***/
  id: number;
  /***/
  date: Date;
  /***/
  contents: Array<Content>;
  /***/
  momentDays: Array<MomentDay>;
  /***/
  patient: Patient;
}
