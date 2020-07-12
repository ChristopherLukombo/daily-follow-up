import { Component, ViewContainerRef } from "@angular/core";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-loader",
  templateUrl: "./loader.component.html",
  styleUrls: ["./loader.component.scss"],
})
export class LoaderComponent {
  constructor(vcr: ViewContainerRef) {}
}
