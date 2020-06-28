export class DietDTO {
  /***/
  id: number;
  /***/
  name: string;
  /***/
  elementsToCheck: Map<string, number>;

  constructor(id: number, name: string, elementsToCheck: Map<string, number>) {
    this.id = id;
    this.name = name;
    this.elementsToCheck = elementsToCheck;
  }
}
