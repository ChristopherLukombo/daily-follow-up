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
  name: string;
  /***/
  repetition: number;
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
    repetition: number,
    diets: Array<string>,
    texture: string,
    replacement: ReplacementDTO,
    lastModificationDateBy: Date,
    lastModifiedBy: string,
    weeks: Array<WeekDTO>
  ) {
    (this.id = id),
      (this.name =
        diets.toString() + " - " + texture + " - " + startDate + "_" + endDate),
      (this.startDate = startDate),
      (this.endDate = endDate),
      (this.repetition = repetition),
      (this.diets = diets),
      (this.texture = texture),
      (this.replacement = replacement),
      (this.lastModificationDateBy = lastModificationDateBy),
      (this.lastModifiedBy = lastModifiedBy),
      (this.weeks = weeks);
  }
}
