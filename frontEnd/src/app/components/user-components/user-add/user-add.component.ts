import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-user-add",
  templateUrl: "./user-add.component.html",
  styleUrls: ["./user-add.component.scss"],
})
export class UserAddComponent implements OnInit {
  roles: Map<string, string> = new Map([
    ["ROLE_CAREGIVER", "Aide-soignant"],
    ["ROLE_NUTRITIONIST", "Diététicien"],
  ]);
  role: string;

  selectedButton: number = 0;

  constructor() {}

  ngOnInit(): void {}

  selectRole(role: string, index: number): void {
    this.selectedButton = index;
    this.role = role;
  }
}
