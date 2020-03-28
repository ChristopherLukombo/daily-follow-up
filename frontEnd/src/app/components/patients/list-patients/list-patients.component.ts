import { Component, OnInit } from "@angular/core";
import { faMars } from "@fortawesome/free-solid-svg-icons";
import { faVenus } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-list-patients",
  templateUrl: "./list-patients.component.html",
  styleUrls: ["./list-patients.component.scss"]
})
export class ListPatientsComponent implements OnInit {
  marsLogo = faMars;
  venusLogo = faVenus;
  constructor() {}

  ngOnInit(): void {}
}
