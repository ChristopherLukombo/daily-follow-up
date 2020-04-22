import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { ResultCsvPatient } from "src/app/models/csv/result-csv-patient";
import { HttpEventType } from "@angular/common/http";
import { FileService } from "src/app/services/file/file.service";

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
  progress: number = 0;
  result: ResultCsvPatient;
  error: string;

  constructor(
    private formBuilder: FormBuilder,
    private fileService: FileService
  ) {}

  ngOnInit(): void {
    this.uploadForm = this.formBuilder.group({});
  }

  /**
   * Récuperation du fichier depuis le disque
   * @param files
   */
  handleFile(files: FileList) {
    this.file = undefined;
    this.cleanErrorMessages();
    const input = files.item(0);
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

  /**
   * Vérifie la validité du fichier csv
   * @param file
   * @return true ou false si le fichier est valide
   */
  validExtension(file: File): boolean {
    const extension = file.name.split(".")[1].toLowerCase();
    if (extension !== "csv") return false;
    const type = file.type.toLowerCase();
    return this.validFile.indexOf(type) !== -1;
  }

  /**
   * Importation des patients
   */
  onUpload(): void {
    this.cleanErrorMessages();
    if (!this.file) return;
    this.fileService.uploadPatientsFile(this.file).subscribe(
      (data) => {
        switch (data.type) {
          case HttpEventType.UploadProgress:
            this.progress = Math.round((data.loaded / data.total) * 100);
            break;
          case HttpEventType.Response:
            this.result = data.body;
            this.resetProgressBar();
            break;
        }
      },
      (error) => {
        this.catchError(error);
        this.resetProgressBar();
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
    this.inputError = undefined;
  }

  resetProgressBar(): void {
    setTimeout(() => {
      this.progress = 0;
    }, 250);
  }
}
