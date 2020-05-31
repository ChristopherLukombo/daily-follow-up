export class CustomModal {
  /***/
  id: string;
  /***/
  label: string;
  /***/
  target: string;

  constructor(id: string, label: string) {
    this.id = id
      .replace(new RegExp("é", "gi"), "e")
      .replace(new RegExp("î", "gi"), "i");
    this.label = label
      .replace(new RegExp("é", "gi"), "e")
      .replace(new RegExp("î", "gi"), "i");
    this.target = "#" + this.id;
  }
}
