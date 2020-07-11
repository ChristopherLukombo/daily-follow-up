import { Content } from "../../food/content";

export class OrderDTO {
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
  createdDate: string;
  /***/
  lastModifBy: string;
  /***/
  lastModifDate: string;
  /***/
  patientId: number;

  constructor(
    id: number,
    moment: string,
    entry: Content,
    dish: Content,
    garnish: Content,
    dairyProduct: Content,
    dessert: Content,
    deliveryDate: string,
    orderStatus: string,
    createdBy: string,
    createdDate: string,
    lastModifBy: string,
    lastModifDate: string,
    patientId: number
  ) {
    this.id = id;
    (this.moment = moment),
      (this.entry = entry),
      (this.dish = dish),
      (this.garnish = garnish),
      (this.dairyProduct = dairyProduct),
      (this.dessert = dessert),
      (this.deliveryDate = deliveryDate),
      (this.orderStatus = orderStatus),
      (this.createdBy = createdBy),
      (this.createdDate = createdDate),
      (this.lastModifBy = lastModifBy),
      (this.lastModifDate = lastModifDate),
      (this.patientId = patientId);
  }
}
