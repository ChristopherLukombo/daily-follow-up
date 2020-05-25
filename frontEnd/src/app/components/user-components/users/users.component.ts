import { Component, OnInit } from "@angular/core";
import { UserService } from "src/app/services/user/user.service";
import { Caregiver } from "src/app/models/user/caregiver";
import { User } from "src/app/models/user/user";
import { ClinicService } from "src/app/services/clinic/clinic.service";
import { Floor } from "src/app/models/clinic/floor";
import { forkJoin } from "rxjs";

@Component({
  selector: "app-users",
  templateUrl: "./users.component.html",
  styleUrls: ["./users.component.scss"],
})
export class UsersComponent implements OnInit {
  caregivers: Caregiver[] = [];
  floors: Floor[] = [];

  error: string;
  loading: Boolean = false;

  constructor(
    private userService: UserService,
    private clinicService: ClinicService
  ) {}

  ngOnInit(): void {
    this.loading = true;
    let allCaregivers = this.userService.getAllCaregivers();
    let allFloors = this.clinicService.getAllFloors();
    forkJoin([allCaregivers, allFloors]).subscribe(
      (datas) => {
        this.caregivers = datas[0];
        this.floors = datas[1];
        this.loading = false;
      },
      (error) => {
        this.error = this.getError(error);
        this.loading = false;
      }
    );
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  getError(error: number): string {
    if (error && error === 401) {
      return "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else {
      return "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
