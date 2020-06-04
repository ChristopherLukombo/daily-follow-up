import { MomentDayDTO } from "../dto/food/moment-dayDTO";

export class MomentDayCustomInfos {
  /***/
  week: number;
  /***/
  day: string;
  /***/
  momentDayDTO: MomentDayDTO;

  constructor(week: number, day: string, momentDayDTO: MomentDayDTO) {
    this.week = week;
    this.day = day;
    this.momentDayDTO = momentDayDTO;
  }
}
