import { Component, OnInit, Input } from "@angular/core";
import { faPlus, faMinus } from "@fortawesome/free-solid-svg-icons";
import {
  FormGroup,
  Validators,
  FormBuilder,
  FormControl,
  FormArray,
  ValidatorFn,
} from "@angular/forms";
import { Diet } from "src/app/models/patient/diet";
import { Texture } from "src/app/models/food/texture";

@Component({
  selector: "app-form-patient-edit",
  templateUrl: "./form-patient-edit.component.html",
  styleUrls: ["./form-patient-edit.component.scss"],
})
export class FormPatientEditComponent implements OnInit {
  addLogo = faPlus;
  removeLogo = faMinus;

  illegalInputInt: string[] = [".", "e", ",", "-", "+", "*"];
  illegalInputFloat: string[] = ["e", "-", "+", "*"];

  form: FormGroup;
  submitted: boolean = false;
  sexes: string[] = ["Homme", "Femme"];
  situations: string[] = [
    "Marié",
    "Pacsé",
    "Divorcé",
    "Separé",
    "Célibataire",
    "Veuf",
  ];
  jobs: string[] = ["En activité", "Sans activité", "Retraité"];
  bloodGroups: string[] = ["A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"];
  @Input() dietsAvailable: Diet[] = [];
  @Input() texturesAvailable: Texture[] = [];
  allergies: string[] = [];

  updating: boolean = false;
  error: string;

  constructor() {}

  ngOnInit(): void {}
}
