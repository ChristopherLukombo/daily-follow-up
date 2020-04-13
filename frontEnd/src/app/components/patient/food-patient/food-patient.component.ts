import { Component, OnInit, Input } from "@angular/core";
import { Patient } from "src/app/models/patient/patient";
import { PatientService } from "src/app/services/patient/patient.service";
import { PatientDTO } from "src/app/models/dto/patientDTO";
import { Comment } from "src/app/models/patient/comment";

@Component({
  selector: "app-food-patient",
  templateUrl: "./food-patient.component.html",
  styleUrls: ["./food-patient.component.scss"],
})
export class FoodPatientComponent implements OnInit {
  @Input() patient: Patient;

  loading: Boolean;
  error: string;

  constructor(private patientService: PatientService) {}

  ngOnInit(): void {}

  newComment(comment: Comment): void {
    this.error = undefined;
    this.loading = true;
    let dto = this.getPatientDTO();
    dto.comment = comment;
    this.patientService.updatePatient(dto).subscribe(
      (data) => {
        this.loading = false;
        this.patient = data;
      },
      (error) => {
        this.loading = false;
        this.catchError(error);
      }
    );
  }

  /**
   * Géneration du DTO
   * @return le DTO du patient
   */
  getPatientDTO(): PatientDTO {
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
      this.patient.comment,
      this.patient.roomId
    );

    return dto;
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  catchError(error: number): void {
    if (error != undefined && error == 403) {
      this.error =
        "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else {
      this.error = "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
