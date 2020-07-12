import { Component, OnInit, Input } from "@angular/core";
import { faEdit, faUndoAlt, faCheck } from "@fortawesome/free-solid-svg-icons";
import { Comment } from "src/app/models/patient/comment";
import { LoginService } from "src/app/services/login/login.service";
import { Patient } from "src/app/models/patient/patient";
import { PatientService } from "src/app/services/patient/patient.service";
import { PatientDTO } from "src/app/models/dto/patient/patientDTO";
import { ToastrService } from "ngx-toastr";
import { CommentDTO } from "src/app/models/dto/patient/commentDTO";
import { TypeMessage } from "src/app/models/utils/message-enum";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-comment-patient",
  templateUrl: "./comment-patient.component.html",
  styleUrls: ["./comment-patient.component.scss"],
})
export class CommentPatientComponent implements OnInit {
  editLogo = faEdit;
  cancelLogo = faUndoAlt;
  submitLogo = faCheck;

  @Input() patient: Patient;
  @Input() isEditable: boolean = false;
  comment: Comment;

  commentEdition: Boolean = false;
  content: string = "";

  loading: boolean;

  constructor(
    private loginService: LoginService,
    private patientService: PatientService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.patient) {
      this.comment = this.patient.comment;
    }
  }

  onEditForm(): void {
    this.content =
      !this.comment || !this.comment.content ? "" : this.comment.content;
    this.commentEdition = true;
  }

  onCancelForm(): void {
    this.commentEdition = false;
  }

  onSubmitForm(): void {
    const editedComment = this.getCommentDTO();
    this.postComment(editedComment);
    this.commentEdition = false;
  }

  getCommentDTO(): CommentDTO {
    return new CommentDTO(
      this.comment ? this.comment.id : null,
      this.content,
      this.loginService.getTokenPseudo(),
      new Date()
    );
  }

  postComment(comment: CommentDTO): void {
    this.loading = true;
    let dto = this.getPatientDTO(comment);
    this.patientService.updatePatient(dto).subscribe(
      (data) => {
        this.patient = data;
        this.comment = this.patient.comment;
        this.loading = false;
        this.toastrService.success(
          "Le commentaire du patient a été mis à jour",
          "Édition terminée !"
        );
      },
      (error) => {
        this.loading = false;
        this.toastrService.error(this.getError(error), "Oops !");
      }
    );
  }

  /**
   * Géneration du DTO
   * @return le DTO du patient
   */
  getPatientDTO(comment: CommentDTO): PatientDTO {
    const dto = new PatientDTO(
      this.patient.id,
      this.patient.firstName,
      this.patient.lastName,
      this.patient.email,
      this.patient.situation,
      this.patient.dateOfBirth,
      this.patient.address,
      this.patient.phoneNumber,
      this.patient.mobilePhone,
      this.patient.job,
      this.patient.bloodGroup,
      this.patient.height,
      this.patient.weight,
      this.patient.sex,
      this.patient.state,
      this.patient.texture,
      this.patient.diets,
      this.patient.allergies,
      this.patient.orders,
      comment,
      this.patient.roomId
    );
    return dto;
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getError(error: number): string {
    if (error === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
