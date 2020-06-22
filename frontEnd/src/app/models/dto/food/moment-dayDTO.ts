import { Content } from "../../food/content";

export class MomentDayDTO {
  /***/
  id: number;
  /***/
  name: string;
  /***/
  entry: Content;
  /***/
  dish: Content;
  /***/
  garnish: Content;
  /***/
  dairyProduct: Content;
  /***/
  dessert: Content;

  constructor(
    id: number,
    name: string,
    entry: Content,
    dish: Content,
    garnish: Content,
    dairyProduct: Content,
    dessert: Content
  ) {
    this.id = id;
    this.name = name;
    this.entry = entry;
    this.dish = dish;
    this.garnish = garnish;
    this.dairyProduct = dairyProduct;
    this.dessert = dessert;
  }
}
