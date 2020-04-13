import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { faEdit, faUndoAlt, faCheck } from "@fortawesome/free-solid-svg-icons";
import { Comment } from "src/app/models/patient/comment";
import { LoginService } from "src/app/services/login/login.service";

@Component({
  selector: "app-comment-patient",
  templateUrl: "./comment-patient.component.html",
  styleUrls: ["./comment-patient.component.scss"],
})
export class CommentPatientComponent implements OnInit {
  editLogo = faEdit;
  cancelLogo = faUndoAlt;
  submitLogo = faCheck;

  @Input() comment: Comment;

  commentEdition: Boolean = false;
  content: string = "";
  @Output() newComment = new EventEmitter<Comment>();

  constructor(private loginService: LoginService) {}

  ngOnInit(): void {}

  onEditForm(): void {
    this.content =
      !this.comment || !this.comment.content ? "" : this.comment.content;
    this.commentEdition = true;
  }

  onCancelForm(): void {
    this.commentEdition = false;
  }

  onSubmitForm(): void {
    let editedComment = !this.comment ? new Comment() : this.comment;
    editedComment.content = this.content;
    editedComment.lastModification = new Date();
    editedComment.pseudo = this.loginService.getTokenPseudo();
    this.postComment(editedComment);

    this.commentEdition = false;
  }

  postComment(comment: Comment): void {
    this.newComment.emit(comment);
  }
}
