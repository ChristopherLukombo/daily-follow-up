import { User } from "./user";
import { Texture } from "./texture";
import { Diet } from "./diet";
import { Allergy } from "./allergy";

export class Patient {
  /***/
  id: number;
  /***/
  user: User;
  /***/
  state: boolean;
  /***/
  texture: Texture;
  /***/
  diets: Array<Diet>;
  /***/
  allergies: Array<Allergy>;
}
