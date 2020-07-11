import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { ResultCsvPatient } from "src/app/models/csv/result-csv-patient";
import { HttpEventType, HttpErrorResponse } from "@angular/common/http";
import { FileService } from "src/app/services/file/file.service";
import { ToastrService } from "ngx-toastr";
import { TypeMessage } from "src/app/models/utils/message-enum";

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
  error: string; // test
  haveToConfirm: boolean = false; // test

  constructor(
    private formBuilder: FormBuilder,
    private fileService: FileService,
    private toastrService: ToastrService
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
            this.toastrService.success(
              "Les patients ont bien été crées",
              "Opération terminée !"
            );
            break;
        }
      },
      (error) => {
        this.error = this.getCustomError(error);
        this.resetProgressBar();
      }
    );
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getCustomError(error: HttpErrorResponse): string {
    if (error && error.status === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else if (error && error.status === 409) {
      return this.removeTriggerTrace(error.error.message);
    } else if (error && error.status === 500) {
      let message: string = error.error.detailedMessage;
      if (message === TypeMessage.PATIENT_OR_PATIENTS_ALREADY_EXIST)
        this.haveToConfirm = true;
      return message;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }

  /**
   * Retire les notions techniques et les messages provenant directement de la base
   * @param message
   */
  removeTriggerTrace(message: string): string {
    return message.split("Où")[0].replace("ERREUR:", "");
  }

  /**
   * Suppression des msg d'erreurs
   */
  cleanErrorMessages(): void {
    this.error = undefined;
    this.inputError = undefined;
    this.haveToConfirm = false;
  }

  resetProgressBar(): void {
    setTimeout(() => {
      this.progress = 0;
    }, 250);
  }
}
