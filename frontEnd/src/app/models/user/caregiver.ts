import { User } from "./user";
import { Floor } from "../clinic/floor";

export class Caregiver {
  /***/
  id: number;
  /***/
  user: User;
  /***/
  floor: Floor;
}
