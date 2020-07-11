import { Component, OnInit } from "@angular/core";
import { faClock, faRecycle, faLock } from "@fortawesome/free-solid-svg-icons";
import { Patient } from "src/app/models/patient/patient";
import { ActivatedRoute, Router } from "@angular/router";
import { PatientService } from "src/app/services/patient/patient.service";
import { ToastrService } from "ngx-toastr";
import { mergeMap } from "rxjs/operators";
import { OrderCustomInfos } from "src/app/models/utils/order-custom-infos";
import { OrderService } from "src/app/services/order/order.service";

@Component({
  selector: "app-patient",
  templateUrl: "./patient.component.html",
  styleUrls: ["./patient.component.scss"],
})
export class PatientComponent implements OnInit {
  lockLogo = faLock;
  historyLogo = faClock;
  restoreLogo = faRecycle;

  patient: Patient;

  orderDeliveryDate: string;
  moments: string[] = ["Déjeuner", "Dîner"];
  orderDeliveryMoment: string;

  btnDelete: string = "Supprimer le patient";
  confirmDelete: string =
    "Une fois supprimé, le patient se retrouvera dans la liste des anciens patients.";
  deleting: boolean = false;
  restoring: boolean = false;

  error: string;
  warning: string;
  loading: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private patientService: PatientService,
    private toastrService: ToastrService,
    private orderService: OrderService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.route.queryParams.forEach((params) => {
      this.patientService.getPatient(parseInt(params["id"])).subscribe(
        (data) => {
          this.loading = false;
          if (data) {
            this.patient = data;
          } else {
            this.patientDoesNotExist();
          }
        },
        (error) => {
          this.loading = false;
          this.catchError(error);
        }
      );
    });
  }

  patientDoesNotExist(): void {
    this.warning = "Ce patient n'existe pas. Veuillez réessayer.";
  }

  deletePatient(): void {
    this.deleting = true;
    this.route.queryParams.forEach((params) => {
      this.patientService
        .deletePatient(parseInt(params["id"]))
        .pipe(
          mergeMap(() => this.patientService.getPatient(parseInt(params["id"])))
        )
        .subscribe(
          (data) => {
            this.patient = data;
            this.deleting = false;
            this.toastrService.success(
              "Le patient a bien été supprimé",
              "Suppression réussie !"
            );
          },
          () => {
            this.deleting = false;
            this.toastrService.error(
              "Une erreur s'est produite, veuillez réessayer plus tard",
              "Oops !"
            );
          }
        );
    });
  }

  restorePatient(): void {
    this.restoring = true;
    this.route.queryParams.forEach((params) => {
      this.patientService
        .restorePatient(parseInt(params["id"]))
        .pipe(
          mergeMap(() => this.patientService.getPatient(parseInt(params["id"])))
        )
        .subscribe(
          (data) => {
            this.patient = data;
            this.restoring = false;
            this.toastrService.success(
              "Le patient est de retour à la clinique",
              "Restauration réussie !"
            );
          },
          () => {
            this.restoring = false;
            this.toastrService.error(
              "Une erreur s'est produite, veuillez réessayer plus tard",
              "Oops !"
            );
          }
        );
    });
  }

  onOrder(): void {
    if (!this.orderDeliveryDate || !this.orderDeliveryMoment) return;
    let infos: OrderCustomInfos = new OrderCustomInfos(
      this.orderDeliveryDate,
      this.orderDeliveryMoment
    );
    this.orderService.storeOrderInfosToLocal(infos);
    this.router.navigate(["/order/add"], {
      queryParams: { id: this.patient.id },
    });
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  catchError(error: number): void {
    if (error && error === 401) {
      this.error =
        "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else {
      this.error = "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
