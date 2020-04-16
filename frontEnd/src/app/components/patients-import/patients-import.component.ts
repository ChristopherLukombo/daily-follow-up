import { Component, OnInit, ChangeDetectorRef } from "@angular/core";
import {
  Validators,
  FormBuilder,
  FormGroup,
  FormControl,
  AbstractControl,
  ValidatorFn,
} from "@angular/forms";

@Component({
  selector: "app-patients-import",
  templateUrl: "./patients-import.component.html",
  styleUrls: ["./patients-import.component.scss"],
})
export class PatientsImportComponent implements OnInit {
  file: File = null;
  uploadForm: FormGroup;
  inputError: string;
  error: string;

  constructor(private formBuilder: FormBuilder) {}

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
    const valid =
      type == "application/vnd.ms-excel" ||
      type == "text/csv" ||
      type == "text/plain";
    return valid ? true : false;
  }

  onUpload(): void {
    if (this.uploadForm.invalid) {
      console.log("no valid, no upload !");
      return;
    }
    console.log("upload !");
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  catchError(error: number): void {
    if (error != undefined && error == 403) {
      this.error =
        "Le nom d'utilisateur et le mot de passe ne correspondent pas.";
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
