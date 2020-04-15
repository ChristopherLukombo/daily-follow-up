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
  submitted: boolean = false;
  error: string;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm() {
    const target = {
      file: [null, Validators.required],
    };
    this.uploadForm = this.formBuilder.group(target);
  }

  get f() {
    return this.uploadForm.controls;
  }

  handleFile(files: FileList) {
    const input = files.item(0);
    console.log(this.uploadForm.controls.file.value);
    if (input && this.validExtension(input)) {
      this.file = input;
      //console.log(files.item(0));
      //console.log(this.file.name);
    } else {
      console.log("l'extension doit etre du csv");
      this.uploadForm.controls.file.setErrors({ incorrect: true });
    }
  }

  // validFileExtension(type: string): ValidatorFn {
  //   return (control: AbstractControl): { [key: string]: any } | null => {
  //     const extension = control.value.name.split(".")[1].toLowerCase();
  //     const unvalidExtension = extension !== type.toLowerCase();
  //     return unvalidExtension
  //       ? { unvalidFileExtension: { value: control.value } }
  //       : null;
  //   };
  // }

  validExtension(file: File) {
    const extension = file.name.split(".")[1].toLowerCase();
    return extension == "csv" ? true : false;
  }

  onUpload(): void {
    this.submitted = true;
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
