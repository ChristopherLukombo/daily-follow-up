import { Component, OnInit, Input } from "@angular/core";
import { Content } from "src/app/models/food/content";

@Component({
  selector: "app-diff-declined-content-day",
  templateUrl: "./diff-declined-content-day.component.html",
  styleUrls: ["./diff-declined-content-day.component.scss"],
})
export class DiffDeclinedContentDayComponent implements OnInit {
  @Input() from: Content[];
  @Input() to: Content[];

  constructor() {}

  ngOnInit(): void {}
}
