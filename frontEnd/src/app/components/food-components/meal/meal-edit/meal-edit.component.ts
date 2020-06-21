import { Component, OnInit } from "@angular/core";
import { Content } from "src/app/models/food/content";

@Component({
  selector: "app-meal-edit",
  templateUrl: "./meal-edit.component.html",
  styleUrls: ["./meal-edit.component.scss"],
})
export class MealEditComponent implements OnInit {
  content: Content;

  loading: boolean = false;
  warning: string;
  error: string;

  constructor() {}

  ngOnInit(): void {}
}
