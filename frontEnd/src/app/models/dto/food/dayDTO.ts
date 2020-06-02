import { MomentDayDTO } from "./moment-dayDTO";

export class DayDTO {
  /***/
  id: number;
  /***/
  name: string;
  /***/
  momentsDays: Array<MomentDayDTO>;

  constructor(id: number, name: string, momentsDays: Array<MomentDayDTO>) {
    this.id = id;
    this.name = name;
    this.momentsDays = momentsDays;
  }
}
