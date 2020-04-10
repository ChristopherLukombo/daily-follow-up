import { Patient } from "./patient";
import { Content } from "../food/content";
import { MomentDay } from "../food/momentDay";

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
