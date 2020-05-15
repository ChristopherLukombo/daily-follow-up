import { TextureDTO } from "./textureDTO";

export class ContentDTO {
  /***/
  id: number;
  /***/
  name: string;
  /***/
  groupName: string;
  /***/
  subGroupName: string;
  /***/
  subSubGroupName: string;
  /***/
  calories: string;
  /***/
  protein: string;
  /***/
  carbohydrate: string;
  /***/
  lipids: string;
  /***/
  sugars: string;
  /***/
  foodFibres: string;
  /***/
  agSaturates: string;
  /***/
  salt: string;
  /***/
  typeMeal: Array<string>;
  /***/
  imageUrl: string;

  constructor(
    id: number,
    name: string,
    groupName: string,
    subGroupName: string,
    subSubGroupName: string,
    calories: string,
    protein: string,
    carbohydrate: string,
    lipids: string,
    sugars: string,
    foodFibres: string,
    agSaturates: string,
    salt: string,
    typeMeal: Array<string>,
    imageUrl: string
  ) {
    this.id = id;
    this.name = name;
    this.groupName = groupName;
    this.subGroupName = subGroupName;
    this.subSubGroupName = subSubGroupName;
    this.calories = calories;
    this.protein = protein;
    this.carbohydrate = carbohydrate;
    this.lipids = lipids;
    this.sugars = sugars;
    this.foodFibres = foodFibres;
    this.agSaturates = agSaturates;
    this.salt = salt;
    this.typeMeal = typeMeal;
    this.imageUrl = imageUrl;
  }
}
