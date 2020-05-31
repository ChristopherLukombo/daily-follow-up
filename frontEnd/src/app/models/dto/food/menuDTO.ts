import { WeekDTO } from "./weekDTO";
import { Diet } from "../../patient/diet";
import { Content } from "../../food/content";

export class MenuDTO {
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
  weeks: Array<WeekDTO>;

  constructor(
    id: number,
    startDate: string,
    endDate: string,
    diet: Diet,
    texture: string,
    replacements: Array<Content>,
    lastModificationDateBy: Date,
    lastModifiedBy: string,
    weeks: Array<WeekDTO>
  ) {
    (this.id = id),
      (this.startDate = startDate),
      (this.endDate = endDate),
      (this.diet = diet),
      (this.texture = texture),
      (this.replacements = replacements),
      (this.lastModificationDateBy = lastModificationDateBy),
      (this.lastModifiedBy = lastModifiedBy),
      (this.weeks = weeks);
  }
}
