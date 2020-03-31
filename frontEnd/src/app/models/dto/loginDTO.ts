export class LoginDTO {
  /***/
  password: string;
  /***/
  username: string;

  constructor(username: string, password: string) {
    this.password = password;
    this.username = username;
  }
}
