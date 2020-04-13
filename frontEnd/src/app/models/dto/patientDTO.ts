import { Address } from "../patient/address";
import { Texture } from "../food/texture";
import { Diet } from "../patient/diet";
import { Allergy } from "../patient/allergy";
import { Order } from "../patient/order";
import { Comment } from "../patient/comment";

export class PatientDTO {
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

  constructor(
    id: number,
    firstName: string,
    lastName: string,
    email: string,
    situation: string,
    dateOfBirth: Date,
    address: Address,
    phoneNumber: string,
    mobilePhone: string,
    job: string,
    bloodGroup: string,
    height: number,
    weight: number,
    sex: string,
    state: boolean,
    texture: Texture,
    diets: Array<Diet>,
    allergies: Array<Allergy>,
    orders: Array<Order>,
    comment: Comment,
    roomId: number
  ) {
    (this.id = id),
      (this.firstName = firstName),
      (this.lastName = lastName),
      (this.email = email),
      (this.situation = situation),
      (this.dateOfBirth = dateOfBirth),
      (this.address = address),
      (this.phoneNumber = phoneNumber),
      (this.mobilePhone = mobilePhone),
      (this.job = job),
      (this.bloodGroup = bloodGroup),
      (this.height = height),
      (this.weight = weight),
      (this.sex = sex),
      (this.state = state),
      (this.texture = texture),
      (this.diets = diets),
      (this.allergies = allergies),
      (this.orders = orders),
      (this.comment = comment),
      (this.roomId = roomId);
  }
}
