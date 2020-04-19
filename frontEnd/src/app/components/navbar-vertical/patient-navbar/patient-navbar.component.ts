import { Component, OnInit } from "@angular/core";
import {
  faUserPlus,
  faUserFriends,
  faUpload,
  faSearch,
} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-patient-navbar",
  templateUrl: "./patient-navbar.component.html",
  styleUrls: ["./patient-navbar.component.scss"],
})
export class PatientNavbarComponent implements OnInit {
  addLogo = faUserPlus;
  importLogo = faUpload;
  oldLogo = faUserFriends;
  allLogo = faSearch;

  constructor() {}

  ngOnInit(): void {}
}
