import { Component, OnInit } from "@angular/core";
import { Patient } from "src/app/models/patient/patient";
import { ActivatedRoute } from "@angular/router";
import { PatientService } from "src/app/services/patient/patient.service";
import { TypeMessage } from "src/app/models/utils/message-enum";
import { OrderService } from "src/app/services/order/order.service";
import { OrderCustomInfos } from "src/app/models/utils/order-custom-infos";

@Component({
  selector: "app-order-add",
  templateUrl: "./order-add.component.html",
  styleUrls: ["./order-add.component.scss"],
})
export class OrderAddComponent implements OnInit {
  patient: Patient;
  deliveryDate: string;
  moment: string;

  loading: boolean = false;
  error: string;

  warning: string;

  strict: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private patientService: PatientService,
    private orderService: OrderService
  ) {}

  // TODO : récupérer les bonnes suggestions en fonction du menus actuel + carte de remplacement + filtre ou non en fonction du régime (strict/allegé)
  // TODO : au submit du formulaire, faire gaffe à bien retirer le infosOrder du localstorage
  ngOnInit(): void {
    this.loading = true;
    this.route.queryParams.subscribe((params) => {
      this.patientService.getPatient(parseInt(params["id"])).subscribe(
        (data) => {
          if (data) {
            this.patient = data;
            this.getOrderInfosFromLocal();
          } else {
            this.warning = TypeMessage.PATIENT_DOES_NOT_EXIST;
          }
          this.loading = false;
        },
        (error) => {
          this.error = this.getError(error);
          this.loading = false;
        }
      );
    });
  }

  getOrderInfosFromLocal(): void {
    let orderInfos: OrderCustomInfos = this.orderService.getOrderInfosFromLocal();
    if (orderInfos) {
      this.deliveryDate = orderInfos.deliveryDate;
      this.moment = orderInfos.moment;
    } else {
      this.warning = TypeMessage.ORDER_INFOS_DOES_NOT_EXIST;
    }
  }

  setStrict(value: boolean) {
    this.strict = value;
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getError(error: number): string {
    if (error && error === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
