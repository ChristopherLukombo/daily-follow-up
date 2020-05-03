export class AddressDTO {
  /***/
  id: number;
  /***/
  streetName: string;
  /***/
  city: string;
  /***/
  postalCode: string;

  constructor(
    id: number,
    streetName: string,
    city: string,
    postalCode: string
  ) {
    this.id = id;
    this.streetName = streetName;
    this.city = city;
    this.postalCode = postalCode;
  }
}
