import { Component, Input } from "@angular/core";

@Component({
  selector: "tabs",
  template: ` <ul class="nav nav-tabs">
      <li
        *ngFor="let tab of tabs"
        class="nav-item nav-link"
        [class.active]="tab.active"
        (click)="selectTab(tab)"
      >
        {{ tab.tabTitle }}
      </li>
    </ul>
    <ng-content></ng-content>`,
  styleUrls: ["./tabs.component.scss"],
})
export class Tabs {
  tabs: Tab[] = [];

  addTab(tab: Tab) {
    if (this.tabs.length === 0) {
      tab.active = true;
    }
    this.tabs.push(tab);
  }

  selectTab(tab: Tab) {
    this.tabs.forEach((tab) => {
      tab.active = false;
    });
    tab.active = true;
  }
}

@Component({
  selector: "tab",
  template: `
    <div [hidden]="!active">
      <ng-content></ng-content>
    </div>
  `,
})
export class Tab {
  @Input() tabTitle;
  active: boolean = false;

  constructor(tabs: Tabs) {
    tabs.addTab(this);
  }
}
