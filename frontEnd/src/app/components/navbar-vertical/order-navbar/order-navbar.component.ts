import { Component, OnInit } from "@angular/core";
import { faClipboardList } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-order-navbar",
  templateUrl: "./order-navbar.component.html",
  styleUrls: ["./order-navbar.component.scss"],
})
export class OrderNavbarComponent implements OnInit {
  orderLogo = faClipboardList;

  constructor() {}

  ngOnInit(): void {}
}
