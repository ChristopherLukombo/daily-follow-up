import { Component, OnInit, Input } from "@angular/core";
import { OrderService } from "src/app/services/order/order.service";
import { PatientService } from "src/app/services/patient/patient.service";
import { Patient } from "src/app/models/patient/patient";
import { Order } from "src/app/models/patient/order";
import { forkJoin } from "rxjs";
import { ClinicService } from "src/app/services/clinic/clinic.service";
import { Floor } from "src/app/models/clinic/floor";
import { TypeMessage } from "src/app/models/utils/message-enum";

@Component({
  selector: "app-list-order-by-date",
  templateUrl: "./list-order-by-date.component.html",
  styleUrls: ["./list-order-by-date.component.scss"],
})
export class ListOrderByDateComponent implements OnInit {
  @Input() date: string;
  @Input() moment: string;

  // floors: Floor[] = [];
  // selectedFloor: Floor;

  patients: Patient[] = [];
  orders: Order[] = [];

  ordersOfPatients: Map<Patient, Order> = new Map<Patient, Order>();

  loading: boolean = false;
  error: string;

  constructor(
    private clinicService: ClinicService,
    private orderService: OrderService,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {
    console.log("de");
    // this.loading = true;
    // this.clinicService.getAllFloors().subscribe(
    //   (data) => {
    //     this.floors = data;
    //     this.loading = false;
    //     this.selectFloor(this.floors[0]);
    //   },
    //   (error) => {
    //     this.error = this.getError(error);
    //     this.loading = false;
    //   }
    // );
  }

  // TODO : recup en fonction de la date
  ngOnChanges(): void {
    if (this.date && this.moment) {
      this.loading = true;
      let patients = this.patientService.getAllPatients();
      let orders = this.orderService.getAllOrders();
      forkJoin([patients, orders]).subscribe(
        (datas) => {
          this.patients = datas[0];
          this.orders = datas[1];
          this.mapOrderToPatients(this.patients, this.orders);
          this.loading = false;
        },
        (error) => {
          this.error = this.getError(error);
          this.loading = false;
        }
      );
    }
  }

  mapOrderToPatients(patients: Patient[], orders: Order[]): void {
    this.ordersOfPatients.clear();
    patients.forEach((patient) => {
      this.ordersOfPatients.set(
        patient,
        orders.find((order) => order.patientId === patient.id)
      );
    });
    console.log(this.ordersOfPatients);
  }

  // selectFloor(floor: Floor): void {
  //   this.selectedFloor = floor;
  // }

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
