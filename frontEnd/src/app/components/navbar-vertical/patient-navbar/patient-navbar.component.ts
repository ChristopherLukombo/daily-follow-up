import { Component, OnInit } from "@angular/core";
import {
  faUserPlus,
  faUserFriends,
  faSearch,
} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-patient-navbar",
  templateUrl: "./patient-navbar.component.html",
  styleUrls: ["./patient-navbar.component.scss"],
})
export class PatientNavbarComponent implements OnInit {
  addLogo = faUserPlus;
  addManyLogo = faUserFriends;
  allLogo = faSearch;

  constructor() {}

  ngOnInit(): void {}
}
