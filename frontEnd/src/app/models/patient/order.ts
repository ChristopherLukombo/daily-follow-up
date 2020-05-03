import { Patient } from "./patient";
import { MomentDay } from "../food/moment-day";
import { ContentDTO } from "../dto/food/contentDTO";

export class Order {
  /***/
  id: number;
  /***/
  date: Date;
  /***/
  contents: Array<ContentDTO>;
  /***/
  momentDays: Array<MomentDay>;
  /***/
  patient: Patient;
}
