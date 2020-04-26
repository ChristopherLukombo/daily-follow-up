export class FormCheckbox {
  /***/
  name: string;
  /***/
  value: string;
  /***/
  selected: boolean;

  constructor(name: string, value: string = name, selected: boolean = false) {
    this.name = name;
    this.value = value;
    this.selected = selected;
  }
}
