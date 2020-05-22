export class ContentDTO {
  /***/
  id: number;
  /***/
  code: number;
  /***/
  name: string;
  /***/
  groupName: string;
  /***/
  subGroupName: string;
  /***/
  subSubGroupName: string;
  /***/
  calories: number;
  /***/
  protein: number;
  /***/
  carbohydrate: number;
  /***/
  lipids: number;
  /***/
  sugars: number;
  /***/
  foodFibres: number;
  /***/
  agSaturates: number;
  /***/
  salt: number;
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
    calories: number,
    protein: number,
    carbohydrate: number,
    lipids: number,
    sugars: number,
    foodFibres: number,
    agSaturates: number,
    salt: number,
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
