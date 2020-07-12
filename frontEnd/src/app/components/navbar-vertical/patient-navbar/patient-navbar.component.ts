import { Component, OnInit } from "@angular/core";
import {
  faUserPlus,
  faUserFriends,
  faUpload,
  faUserLock,
} from "@fortawesome/free-solid-svg-icons";
import { LoginService } from "src/app/services/login/login.service";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-patient-navbar",
  templateUrl: "./patient-navbar.component.html",
  styleUrls: ["./patient-navbar.component.scss"],
})
export class PatientNavbarComponent implements OnInit {
  addLogo = faUserPlus;
  importLogo = faUpload;
  oldLogo = faUserLock;
  allLogo = faUserFriends;

  isCaregiver: boolean = false;

  constructor(private loginService: LoginService) {}

  ngOnInit(): void {
    this.isCaregiver = this.loginService.isCaregiver();
  }
}
