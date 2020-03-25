import { Component, OnInit } from "@angular/core";
import { faUserPlus } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-patients",
  templateUrl: "./patients.component.html",
  styleUrls: ["./patients.component.scss"]
})
export class PatientsComponent implements OnInit {
  addLogo = faUserPlus;

  constructor() {}

  ngOnInit(): void {}
}
