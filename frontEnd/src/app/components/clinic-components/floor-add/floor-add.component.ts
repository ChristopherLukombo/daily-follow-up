import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-floor-add",
  templateUrl: "./floor-add.component.html",
  styleUrls: ["./floor-add.component.scss"],
})
export class FloorAddComponent implements OnInit {
  loading: boolean = false;
  error: string;

  constructor() {}

  ngOnInit(): void {}
}
