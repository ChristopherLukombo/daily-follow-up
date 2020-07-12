import { Component, OnInit, Input } from "@angular/core";
import { OrderService } from "src/app/services/order/order.service";
import { PatientService } from "src/app/services/patient/patient.service";
import { Patient } from "src/app/models/patient/patient";
import { Order } from "src/app/models/patient/order";
import { forkJoin } from "rxjs";
import { TypeMessage } from "src/app/models/utils/message-enum";
import { ToastrService } from "ngx-toastr";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-list-order-by-date",
  templateUrl: "./list-order-by-date.component.html",
  styleUrls: ["./list-order-by-date.component.scss"],
})
export class ListOrderByDateComponent implements OnInit {
  @Input() date: string;
  @Input() moment: string;

  patients: Patient[] = [];
  orders: Order[] = [];

  ordersOfPatients: Map<Patient, Order> = new Map<Patient, Order>();

  loading: boolean = false;
  error: string;

  constructor(
    private orderService: OrderService,
    private patientService: PatientService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.date && this.moment) {
      this.loading = true;
      let patients = this.patientService.getAllPatients();
      let orders = this.orderService.getOrdersOfTheDate(this.date);
      forkJoin([patients, orders]).subscribe(
        (datas) => {
          this.patients = datas[0];
          this.orders = datas[1] ? datas[1] : null;
          this.mapOrderToPatients(this.patients, this.orders, this.moment);
          this.loading = false;
        },
        (error) => {
          this.error = this.getError(error);
          this.loading = false;
        }
      );
    }
  }

  mapOrderToPatients(
    patients: Patient[],
    orders: Order[],
    moment: string
  ): void {
    if (!orders || !orders.length || !patients || !patients.length) return;
    this.ordersOfPatients.clear();
    patients.forEach((patient) => {
      this.ordersOfPatients.set(
        patient,
        orders.find(
          (order) => order.patientId === patient.id && order.moment === moment
        )
      );
    });
  }

  removeOrderOfPatient(patient: Patient): void {
    let order: Order = this.ordersOfPatients.get(patient);
    if (!order) return;
    this.orderService.deleteOrder(order.id).subscribe(
      (data) => {
        // pour ne pas retirer la commande alors que la modal est en cours de fermeture
        setTimeout(() => {
          this.ordersOfPatients.set(patient, null);
        }, 200);
        this.toastrService.success(
          "La commande de " +
            patient.lastName +
            " " +
            patient.firstName +
            " a bien été supprimée des registres pour le " +
            order.moment +
            " du " +
            order.deliveryDate,
          "Suppression réussie !"
        );
      },
      (error) => {
        this.toastrService.error(this.getError(error), "Oops !");
      }
    );
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  getError(error: number): string {
    if (error && error === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else if (error && error === 403) {
      return TypeMessage.NOT_AUTHORIZED;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
