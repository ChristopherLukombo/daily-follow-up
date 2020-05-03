export class CommentDTO {
  /***/
  id: number;
  /***/
  content: string;
  /***/
  pseudo: string;
  /***/
  lastModification: Date;

  constructor(
    id: number,
    content: string,
    pseudo: string,
    lastModification: Date
  ) {
    this.id = id;
    this.content = content;
    this.pseudo = pseudo;
    this.lastModification = lastModification;
  }
}
