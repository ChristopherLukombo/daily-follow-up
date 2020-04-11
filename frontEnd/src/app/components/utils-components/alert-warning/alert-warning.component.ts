import { Component, OnInit, Input } from "@angular/core";

@Component({
  selector: "app-alert-warning",
  templateUrl: "./alert-warning.component.html",
  styleUrls: ["./alert-warning.component.scss"],
})
export class AlertWarningComponent implements OnInit {
  @Input() title: string = "Attention";
  @Input() content: string;
  @Input() check: Boolean = true;

  constructor() {}

  ngOnInit(): void {}
}
