import { MomentDayDTO } from "./moment-dayDTO";

export class DayDTO {
  /***/
  id: number;
  /***/
  name: string;
  /***/
  momentDays: Array<MomentDayDTO>;

  constructor(id: number, name: string, momentDays: Array<MomentDayDTO>) {
    this.id = id;
    this.name = name;
    this.momentDays = momentDays;
  }
}
