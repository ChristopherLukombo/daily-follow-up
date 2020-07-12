import { Content } from "../food/content";

export class Order {
  /***/
  id: number;
  /***/
  moment: string;
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
  /***/
  deliveryDate: string;
  /***/
  orderStatus: string;
  /***/
  createdBy: string;
  /***/
  createdDate: Date;
  /***/
  lastModifBy: string;
  /***/
  lastModifDate: Date;
  /***/
  patientId: number;
}
