import { TextureDTO } from "./textureDTO";

export class ContentDTO {
  /***/
  id: number;
  /***/
  name: string;
  /***/
  texture: TextureDTO;
  /***/
  typeMeal: string;
  /***/
  salt: boolean;
  /***/
  sugar: boolean;

  constructor(
    id: number,
    name: string,
    texture: TextureDTO,
    typeMeal: string,
    salt: boolean,
    sugar: boolean
  ) {
    this.id = id;
    this.name = name;
    this.texture = texture;
    this.typeMeal = typeMeal;
    this.salt = salt;
    this.sugar = sugar;
  }
}
