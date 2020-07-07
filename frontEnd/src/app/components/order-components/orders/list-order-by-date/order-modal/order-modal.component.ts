import { Component, OnInit, Input } from "@angular/core";
import { faList } from "@fortawesome/free-solid-svg-icons";
import { CustomModal } from "src/app/models/utils/custom-modal";
import { Order } from "src/app/models/patient/order";
import { Patient } from "src/app/models/patient/patient";
import * as moment from "moment";
import { Router } from "@angular/router";

@Component({
  selector: "app-order-modal",
  templateUrl: "./order-modal.component.html",
  styleUrls: ["./order-modal.component.scss"],
})
export class OrderModalComponent implements OnInit {
  editLogo = faList;

  @Input() order: Order;
  @Input() patient: Patient;

  modal: CustomModal;

  isPast: boolean = false;

  constructor(private router: Router) {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.order && this.patient) {
      // Generation du modal unique pour chaque commande de patient
      this.modal = new CustomModal(
        this.order.moment + this.order.deliveryDate + this.order.id,
        this.order.moment + this.order.deliveryDate + this.order.id + "Label"
      );
      this.isPast = this.isInPast();
    }
  }

  isInPast(): boolean {
    return moment().isAfter(moment(this.order.deliveryDate));
  }

  onEdit(): void {
    this.router.navigate(["/order/edit"], {
      queryParams: { id: this.order.id },
    });
  }

  onValidate(): void {}

  onCancel(): void {}
}
