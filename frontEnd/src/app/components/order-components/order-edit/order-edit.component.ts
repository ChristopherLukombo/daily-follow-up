import { Component, OnInit } from "@angular/core";
import { PatientService } from "src/app/services/patient/patient.service";
import { OrderService } from "src/app/services/order/order.service";
import { Order } from "src/app/models/patient/order";
import { Patient } from "src/app/models/patient/patient";
import { ActivatedRoute } from "@angular/router";
import { forkJoin } from "rxjs";
import { TypeMessage } from "src/app/models/utils/message-enum";
import * as moment from "moment";
import { LoginService } from "src/app/services/login/login.service";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-order-edit",
  templateUrl: "./order-edit.component.html",
  styleUrls: ["./order-edit.component.scss"],
})
export class OrderEditComponent implements OnInit {
  order: Order;
  patient: Patient;

  loading: boolean = false;
  error: string;

  warning: string;

  strict: boolean = true;

  isCaregiver: boolean = false;

  constructor(
    private loginService: LoginService,
    private route: ActivatedRoute,
    private patientService: PatientService,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.isCaregiver = this.loginService.isCaregiver();
    this.loading = true;
    this.route.queryParams.subscribe((params) => {
      let actualOrder = this.orderService.getOrder(parseInt(params["id"]));
      let actualPatient = this.patientService.getPatientByOrder(
        parseInt(params["id"])
      );
      forkJoin([actualOrder, actualPatient]).subscribe(
        (datas) => {
          this.loading = false;
          if (!datas[0] || !datas[1]) {
            this.warning = TypeMessage.ORDER_OF_PATIENT_NOT_FOUND;
            return;
          }
          if (!datas[0].deliveryDate || this.isInPast(datas[0].deliveryDate)) {
            this.warning = TypeMessage.OLD_ORDERS_ARE_NOT_EDITABLE;
            return;
          }
          this.order = datas[0];
          this.patient = datas[1];
        },
        (error) => {
          this.error = this.getError(error);
          this.loading = false;
        }
      );
    });
  }

  setStrict(value: boolean) {
    this.strict = value;
  }

  isInPast(date: string): boolean {
    return moment().isAfter(moment(date));
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
