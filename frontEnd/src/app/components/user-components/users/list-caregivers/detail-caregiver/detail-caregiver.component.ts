import { Component, OnInit, Input, EventEmitter, Output } from "@angular/core";
import { UserService } from "src/app/services/user/user.service";
import { ToastrService } from "ngx-toastr";
import { UserDTO } from "src/app/models/dto/user/userDTO";
import { Caregiver } from "src/app/models/user/caregiver";
import { TypeMessage } from "src/app/models/utils/message-enum";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-detail-caregiver",
  templateUrl: "./detail-caregiver.component.html",
  styleUrls: ["./detail-caregiver.component.scss"],
})
export class DetailCaregiverComponent implements OnInit {
  @Input() caregiver: Caregiver;

  updating: boolean = false;
  updatedMessage: string;

  deleting: boolean = false;
  @Output() hasDeleteCaregiver = new EventEmitter<number>();

  constructor(
    private userService: UserService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    this.clearMessages();
  }

  onResetPassword(): void {
    this.updating = true;
    this.clearMessages();
    let newPassword: string = this.userService.generateUserPassword();
    let dto = new UserDTO(
      this.caregiver.user.id,
      this.caregiver.user.pseudo,
      newPassword,
      this.caregiver.user.firstName,
      this.caregiver.user.lastName,
      this.caregiver.user.createDate,
      this.caregiver.user.status,
      this.caregiver.user.imageUrl,
      this.caregiver.user.roleName,
      false
    );
    this.userService.updateUser(dto).subscribe(
      (data) => {
        this.toastrService.success(
          "Le mot de passe a bien été réinitialisé",
          "Réinitialisation terminée !"
        );
        this.updatedMessage = this.getSuccessfullyResetPasswordMsg(
          data.pseudo,
          newPassword
        );
        this.updating = false;
      },
      (error) => {
        this.toastrService.error(this.getError(error), "Oops !");
        this.updating = false;
      }
    );
  }

  onDelete(): void {
    this.deleting = true;
    this.deleteCaregiver(this.caregiver.id);
  }

  deleteCaregiver(id: number): void {
    this.userService.deleteCaregiver(id).subscribe(
      (data) => {
        this.caregiver = null;
        this.hasDeleteCaregiver.emit(id);
        this.toastrService.success(
          "L'utilisateur a bien été supprimé",
          "Suppression réussie !"
        );
        this.deleting = false;
      },
      (error) => {
        this.toastrService.error(this.getError(error), "Oops !");
        this.deleting = false;
      }
    );
  }

  clearMessages(): void {
    this.updatedMessage = null;
  }

  getSuccessfullyResetPasswordMsg(pseudo: string, password: string): string {
    return (
      "Le mot de passe de <strong><code>" +
      pseudo +
      "</code></strong> a bien été réinitialisé. Voici son mot de passe temporaire : <strong><code>" +
      password +
      "</code></strong><br/>Il sera modifié lors de la prochaine connexion à l'application."
    );
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  getError(error: number): string {
    if (error && error === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else if (error && error === 403) {
      return TypeMessage.NOT_AUTHORIZED;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
