import { UserDTO } from "./userDTO";

export class CaregiverDTO {
  /***/
  id: number;
  /***/
  floorId: number;
  /***/
  user: UserDTO;

  constructor(id: number, floorId: number, user: UserDTO) {
    this.id = id;
    this.floorId = floorId;
    this.user = user;
  }
}
