import { Content } from "../../food/content";

export class MomentDayDTO {
  /***/
  id: number;
  /***/
  name: string;
  /***/
  contents: Array<Content>;

  constructor(id: number, name: string, contents: Array<Content>) {
    this.id = id;
    this.name = name;
    this.contents = contents;
  }
}
