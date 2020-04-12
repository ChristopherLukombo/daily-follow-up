import { Component, OnInit, Input } from "@angular/core";
import { faEdit, faUndoAlt, faCheck } from "@fortawesome/free-solid-svg-icons";
import { Comment } from "src/app/models/patient/comment";

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

  constructor() {}

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
    this.commentEdition = false;
  }
}
