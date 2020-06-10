export class DietDTO {
  /***/
  id: number;
  /***/
  name: string;
  /***/
  elementsToCheck: Array<string>;

  constructor(id: number, name: string, elementsToCheck: Array<string>) {
    this.id = id;
    this.name = name;
    this.elementsToCheck = elementsToCheck;
  }
}
