import { Texture } from "../food/texture";
import { Diet } from "./diet";
import { Allergy } from "./allergy";
import { Address } from "./address";
import { Comment } from "./comment";
import { Order } from "./order";

export class Patient {
  /***/
  id: number;
  /***/
  firstName: string;
  /***/
  lastName: string;
  /***/
  email: string;
  /***/
  situation: string;
  /***/
  dateOfBirth: Date;
  /***/
  address: Address;
  /***/
  phoneNumber: string;
  /***/
  mobilePhone: string;
  /***/
  job: string;
  /***/
  bloodGroup: string;
  /***/
  height: number;
  /***/
  weight: number;
  /***/
  sex: string;
  /***/
  state: boolean;
  /***/
  texture: Texture;
  /***/
  diets: Array<Diet>;
  /***/
  allergies: Array<Allergy>;
  /***/
  orders: Array<Order>;
  /***/
  comment: Comment;
  /***/
  roomId: number;
}
