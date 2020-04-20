import { Patient } from "./patient";
import { Content } from "../food/content";
import { MomentDay } from "../food/moment-day";

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
