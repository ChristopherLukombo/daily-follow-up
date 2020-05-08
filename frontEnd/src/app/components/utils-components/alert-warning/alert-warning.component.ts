import { Component, OnInit, Input } from "@angular/core";
import { faExclamationTriangle } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-alert-warning",
  templateUrl: "./alert-warning.component.html",
  styleUrls: ["./alert-warning.component.scss"],
})
export class AlertWarningComponent implements OnInit {
  warningLogo = faExclamationTriangle;

  @Input() content: string;
  @Input() close: boolean = true;

  constructor() {}

  ngOnInit(): void {}
}
