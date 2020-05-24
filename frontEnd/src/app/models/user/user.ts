import { Role } from "./role";

export class User {
  /***/
  id: number;
  /***/
  pseudo: string;
  /***/
  password: string;
  /***/
  firstName: string;
  /***/
  lastName: string;
  /***/
  email: string;
  /***/
  createDate: Date;
  /***/
  status: boolean;
  /***/
  imageUrl: string;
  /***/
  birthDay: Date;
  /***/
  role: Role;
}
