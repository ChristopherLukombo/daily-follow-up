export class UserDTO {
  /***/
  id: number;
  /***/
  pseudo: string;
  /***/
  password: string;
  /***/
  firstName: string;
  /***/
  lastName: string;
  /***/
  createDate: string;
  /***/
  status: boolean;
  /***/
  imageUrl: string;
  /***/
  roleName: string;
  /***/
  hasChangedPassword: boolean;

  constructor(
    id: number,
    pseudo: string,
    password: string,
    firstName: string,
    lastName: string,
    createDate: string,
    status: boolean,
    imageUrl: string,
    roleName: string,
    hasChangedPassword: boolean
  ) {
    this.id = id;
    this.pseudo = pseudo;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.createDate = createDate;
    this.status = status;
    this.imageUrl = imageUrl;
    this.roleName = roleName;
    this.hasChangedPassword = hasChangedPassword;
  }
}
