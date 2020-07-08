import { Component, OnInit, Input } from "@angular/core";
import { OrderService } from "src/app/services/order/order.service";
import { PatientService } from "src/app/services/patient/patient.service";
import { Patient } from "src/app/models/patient/patient";
import { Order } from "src/app/models/patient/order";
import { forkJoin } from "rxjs";
import { TypeMessage } from "src/app/models/utils/message-enum";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { OrderCustomInfos } from "src/app/models/utils/order-custom-infos";
import { Router } from "@angular/router";

@Component({
  selector: "app-list-order-by-date",
  templateUrl: "./list-order-by-date.component.html",
  styleUrls: ["./list-order-by-date.component.scss"],
})
export class ListOrderByDateComponent implements OnInit {
  addLogo = faPlus;

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
    private router: Router
  ) {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.date && this.moment) {
      this.loading = true;
      let patients = this.patientService.getAllPatients();
      let orders = this.orderService.getOrdersByDate(this.date);
      forkJoin([patients, orders]).subscribe(
        (datas) => {
          this.patients = datas[0];
          this.orders = datas[1];
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

  onAddOrder(patientId: number): void {
    let infos: OrderCustomInfos = new OrderCustomInfos(this.date, this.moment);
    this.orderService.storeOrderInfosToLocal(infos);
    this.router.navigate(["/order/add"], { queryParams: { id: patientId } });
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
