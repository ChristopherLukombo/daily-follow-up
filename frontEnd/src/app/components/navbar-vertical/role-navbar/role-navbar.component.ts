import { Component, OnInit } from "@angular/core";
import {
  faUserPlus,
  faUserFriends,
  faUserLock,
} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-role-navbar",
  templateUrl: "./role-navbar.component.html",
  styleUrls: ["./role-navbar.component.scss"],
})
export class RoleNavbarComponent implements OnInit {
  addLogo = faUserPlus;
  oldLogo = faUserLock;
  allLogo = faUserFriends;

  constructor() {}

  ngOnInit(): void {}
}
