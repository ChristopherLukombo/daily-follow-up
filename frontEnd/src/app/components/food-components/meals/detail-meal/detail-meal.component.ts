import { Component, OnInit, Input } from "@angular/core";
import { Content } from "src/app/models/food/content";
import { faAngleDoubleRight } from "@fortawesome/free-solid-svg-icons";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-detail-meal",
  templateUrl: "./detail-meal.component.html",
  styleUrls: ["./detail-meal.component.scss"],
})
export class DetailMealComponent implements OnInit {
  moreDetailsLogo = faAngleDoubleRight;

  @Input() content: Content;

  constructor() {}

  ngOnInit(): void {}
}
