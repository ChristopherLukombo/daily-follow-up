import { Content } from "../../food/content";

export class ReplacementDTO {
  /***/
  id: number;
  /***/
  entries: Array<Content>;
  /***/
  dishes: Array<Content>;
  /***/
  starchyFoods: Array<Content>;
  /***/
  vegetables: Array<Content>;
  /***/
  dairyProducts: Array<Content>;
  /***/
  desserts: Array<Content>;

  constructor(
    id: number,
    entries: Array<Content>,
    dishes: Array<Content>,
    starchyFoods: Array<Content>,
    vegetables: Array<Content>,
    dairyProducts: Array<Content>,
    desserts: Array<Content>
  ) {
    this.id = id;
    this.entries = entries;
    this.dishes = dishes;
    this.starchyFoods = starchyFoods;
    this.vegetables = vegetables;
    this.dairyProducts = dairyProducts;
    this.desserts = desserts;
  }
}
