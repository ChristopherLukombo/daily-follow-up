import { TextureDTO } from "../food/textureDTO";
import { AddressDTO } from "./addressDTO";
import { DietDTO } from "./dietDTO";
import { AllergyDTO } from "./allergyDTO";
import { CommentDTO } from "./commentDTO";
import { OrderDTO } from "./orderDTO";

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
  address: AddressDTO;
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
  texture: TextureDTO;
  /***/
  diets: Array<DietDTO>;
  /***/
  allergies: Array<AllergyDTO>;
  /***/
  orders: Array<OrderDTO>;
  /***/
  comment: CommentDTO;
  /***/
  roomId: number;

  constructor(
    id: number,
    firstName: string,
    lastName: string,
    email: string,
    situation: string,
    dateOfBirth: Date,
    address: AddressDTO,
    phoneNumber: string,
    mobilePhone: string,
    job: string,
    bloodGroup: string,
    height: number,
    weight: number,
    sex: string,
    state: boolean,
    texture: TextureDTO,
    diets: Array<DietDTO>,
    allergies: Array<AllergyDTO>,
    orders: Array<OrderDTO>,
    comment: CommentDTO,
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
