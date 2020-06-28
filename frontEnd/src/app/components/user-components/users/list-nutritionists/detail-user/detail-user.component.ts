import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { User } from "src/app/models/user/user";
import { UserService } from "src/app/services/user/user.service";
import { UserDTO } from "src/app/models/dto/user/userDTO";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-detail-user",
  templateUrl: "./detail-user.component.html",
  styleUrls: ["./detail-user.component.scss"],
})
export class DetailUserComponent implements OnInit {
  @Input() user: User;

  updating: boolean = false;
  updatedMessage: string;

  deleting: boolean = false;
  @Output() hasDeleteUser = new EventEmitter<number>();

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
      this.user.id,
      this.user.pseudo,
      newPassword,
      this.user.firstName,
      this.user.lastName,
      this.user.createDate,
      this.user.status,
      this.user.imageUrl,
      this.user.roleName,
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
    this.deleteNutritionist(this.user.id);
  }

  deleteNutritionist(id: number): void {
    this.userService.deleteUser(id).subscribe(
      (data) => {
        this.user = null;
        this.hasDeleteUser.emit(id);
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
      return "Vous n'êtes plus connecté, veuillez rafraichir le navigateur.";
    } else if (error && error === 403) {
      return "Vous n'êtes plus habilité à cette requête.";
    } else {
      return "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
