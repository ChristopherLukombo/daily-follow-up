import { DayDTO } from "./dayDTO";

export class WeekDTO {
  /***/
  id: number;
  /***/
  number: number;
  /***/
  days: Array<DayDTO>;

  constructor(id: number, number: number, days: Array<DayDTO>) {
    this.id = id;
    this.number = number;
    this.days = days;
  }
}
