import { WeekDTO } from "./weekDTO";
import { ReplacementDTO } from "./replacementDTO";

export class MenuDTO {
  /***/
  diets: Array<string>;
  /***/
  endDate: string;
  /***/
  id: number;
  /***/
  lastModificationDateBy: Date;
  /***/
  lastModifiedBy: string;
  /***/
  replacement: ReplacementDTO;
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
    diets: Array<string>,
    texture: string,
    replacement: ReplacementDTO,
    lastModificationDateBy: Date,
    lastModifiedBy: string,
    weeks: Array<WeekDTO>
  ) {
    (this.id = id),
      (this.startDate = startDate),
      (this.endDate = endDate),
      (this.diets = diets),
      (this.texture = texture),
      (this.replacement = replacement),
      (this.lastModificationDateBy = lastModificationDateBy),
      (this.lastModifiedBy = lastModifiedBy),
      (this.weeks = weeks);
  }
}
