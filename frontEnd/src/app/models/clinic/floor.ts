import { Room } from "./room";

export class Floor {
  /***/
  id: number;
  /***/
  number: number;
  /***/
  rooms: Array<Room>;
  /***/
  state: boolean;
}
