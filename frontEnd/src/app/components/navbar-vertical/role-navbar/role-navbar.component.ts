import { Component, OnInit } from "@angular/core";
import { faUserPlus, faUserFriends } from "@fortawesome/free-solid-svg-icons";
import { LoginService } from "src/app/services/login/login.service";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-role-navbar",
  templateUrl: "./role-navbar.component.html",
  styleUrls: ["./role-navbar.component.scss"],
})
export class RoleNavbarComponent implements OnInit {
  addLogo = faUserPlus;
  allLogo = faUserFriends;

  isCaregiver: boolean = false;

  constructor(private loginService: LoginService) {}

  ngOnInit(): void {
    this.isCaregiver = this.loginService.isCaregiver();
  }
}
