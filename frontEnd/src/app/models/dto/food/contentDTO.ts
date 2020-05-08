import { TextureDTO } from "./textureDTO";

export class ContentDTO {
  /***/
  id: number;
  /***/
  name: string;
  /***/
  texture: TextureDTO;
  /***/
  salt: boolean;
  /***/
  sugar: boolean;

  constructor(
    id: number,
    name: string,
    texture: TextureDTO,
    salt: boolean,
    sugar: boolean
  ) {
    this.id = id;
    this.name = name;
    this.texture = texture;
    this.salt = salt;
    this.sugar = sugar;
  }
}
