import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { Patient } from "src/app/models/patient/patient";
import { Order } from "src/app/models/patient/order";
import { OrderService } from "src/app/services/order/order.service";
import { OrderCustomInfos } from "src/app/models/utils/order-custom-infos";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { TypeMessage } from "src/app/models/utils/message-enum";
import { Status } from "src/app/models/utils/status-enum";
import * as moment from "moment";

@Component({
  selector: "app-order-card",
  templateUrl: "./order-card.component.html",
  styleUrls: ["./order-card.component.scss"],
})
export class OrderCardComponent implements OnInit {
  addLogo = faPlus;

  @Input() date: string;
  @Input() moment: string;

  @Input() patient: Patient;
  @Input() order: Order;

  periodExpired: boolean = false;

  validating: boolean = false;

  @Output() deletedOrderOfPatient = new EventEmitter<Patient>();

  constructor(
    private orderService: OrderService,
    private router: Router,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.date) {
      this.periodExpired = this.isInPast();
    }
  }

  isInPast(): boolean {
    return moment().isAfter(moment(this.date));
  }

  onAddOrder(): void {
    let infos: OrderCustomInfos = new OrderCustomInfos(this.date, this.moment);
    this.orderService.storeOrderInfosToLocal(infos);
    this.router.navigate(["/order/add"], {
      queryParams: { id: this.patient.id },
    });
  }

  onValidateOrder(): void {
    this.validating = false;
    this.orderService.validateOrder(this.order).subscribe(
      (data) => {
        this.validating = false;
        this.order.orderStatus = Status.VALIDATED;
        this.toastrService.success(
          "La commande de " +
            this.patient.lastName +
            " " +
            this.patient.firstName +
            " a bien été validé pour le " +
            this.moment +
            " du " +
            this.date,
          "En cuisine !"
        );
      },
      (error) => {
        this.validating = false;
        this.toastrService.error(this.getError(error), "Oops !");
      }
    );
  }

  removeOrderOfPatient(patient: Patient): void {
    this.deletedOrderOfPatient.emit(patient);
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
