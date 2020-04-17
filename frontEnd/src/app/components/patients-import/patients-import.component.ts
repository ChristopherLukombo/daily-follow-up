import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { PatientService } from "src/app/services/patient/patient.service";
import { Patient } from "src/app/models/patient/patient";

@Component({
  selector: "app-patients-import",
  templateUrl: "./patients-import.component.html",
  styleUrls: ["./patients-import.component.scss"],
})
export class PatientsImportComponent implements OnInit {
  file: File = null;
  uploadForm: FormGroup;
  validFile: Array<String> = new Array(
    "application/vnd.ms-excel",
    "text/csv",
    "text/plain"
  );
  inputError: string;
  result: Patient[] = [];
  error: string;

  constructor(
    private formBuilder: FormBuilder,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm() {
    this.uploadForm = this.formBuilder.group({});
  }

  handleFile(files: FileList) {
    this.file = undefined;
    this.inputError = undefined;
    const input = files.item(0);
    console.log(input);
    if (!input) {
      this.inputError = "Le fichier est requis";
      return;
    }
    if (!this.validExtension(input)) {
      this.inputError = "Le fichier doit être sous format .csv";
      return;
    }
    this.file = input;
  }

  validExtension(file: File): boolean {
    const extension = file.name.split(".")[1].toLowerCase();
    if (extension !== "csv") return false;
    const type = file.type.toLowerCase();
    return this.validFile.indexOf(type) !== 1 ? true : false;
  }

  onUpload(): void {
    this.error = undefined;
    if (!this.file) {
      // a voir
      return;
    }
    console.log("upload !");
    this.patientService.uploadPatientsFile(this.file).subscribe(
      (data) => {
        this.result = data;
        console.log(this.result);
      },
      (error) => {
        this.catchError(error);
      }
    );
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  catchError(error: number): void {
    if (error && error === 403) {
      this.error =
        "Vous n'êtes plus connecté, veuillez rafraichir le navigateur.";
    } else if (error && error === 422) {
      this.error =
        "Le fichier comporte des champs qui ne sont pas valides, \
        veuillez suivre les indications sur les colonnes et les données à insérer.";
    } else if (error && error === 409) {
      this.error =
        "Le fichier comporte un ou plusieurs patient déjà existants.";
    } else {
      this.error = "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
  /**
   * Suppression des msg d'erreurs
   */
  cleanErrorMessages(): void {
    this.error = undefined;
  }
}
