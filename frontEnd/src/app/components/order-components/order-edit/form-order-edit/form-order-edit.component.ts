import { Component, OnInit, Input } from "@angular/core";
import { Content } from "src/app/models/food/content";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Order } from "src/app/models/patient/order";
import { TypeMessage } from "src/app/models/utils/message-enum";
import { Patient } from "src/app/models/patient/patient";
import { ToastrService } from "ngx-toastr";
import { Router } from "@angular/router";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { OrderService } from "src/app/services/order/order.service";
import { OrderDTO } from "src/app/models/dto/patient/orderDTO";
import { MenuUtilsService } from "src/app/services/order/menu-utils.service";
import { Day } from "src/app/models/food/day";
import { MomentDay } from "src/app/models/food/moment-day";

@Component({
  selector: "app-form-order-edit",
  templateUrl: "./form-order-edit.component.html",
  styleUrls: ["./form-order-edit.component.scss"],
})
export class FormOrderEditComponent implements OnInit {
  @Input() patient: Patient;
  @Input() order: Order;
  @Input() strict: boolean = true;

  loading: boolean = false;
  error: string;

  suggestions: Map<string, Content[]> = new Map([
    ["Entrée", []],
    ["Plat", []],
    ["Garniture", []],
    ["P.L", []],
    ["Dessert", []],
  ]);

  form: FormGroup;
  submitted: boolean = false;
  updating: boolean = false;

  btnDelete: string = "Supprimer la commande";
  confirmDelete: string =
    "La commande sera supprimé des registre, et ne partira donc pas en cuisine. Veuillez confirmer pour continuer.";
  deleting: boolean = false;

  constructor(
    private alimentationService: AlimentationService,
    private menuUtilsService: MenuUtilsService,
    private orderService: OrderService,
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.patient && this.order) {
      this.createForm(this.order);
    }
  }

  ngOnChanges(): void {
    if (this.patient && this.order) {
      console.log(this.order.deliveryDate);
      this.getAllContentsAvailable(this.order.deliveryDate, this.order.moment);
    }
  }

  createForm(order: Order) {
    if (!order) return;
    const target = {
      deliveryDate: [
        { value: order.deliveryDate, disabled: true },
        Validators.required,
      ],
      moment: [{ value: order.moment, disabled: true }, Validators.required],
      entry: [order.entry, Validators.required],
      dish: [order.dish, Validators.required],
      garnish: [order.garnish, Validators.required],
      dairyProduct: [order.dairyProduct, Validators.required],
      dessert: [order.dessert, Validators.required],
    };
    this.form = this.formBuilder.group(target);
    if (order.moment === "Dîner") {
      this.form.removeControl("dairyProduct");
      this.suggestions.delete("P.L");
    }
  }

  get f() {
    return this.form.controls;
  }

  // TODO : Gerer les menus strict ou non
  // TODO : get les menus specifiques à la date, et non les currents menus
  getAllContentsAvailable(date: string, moment: string): void {
    this.loading = true;
    this.alimentationService.getCurrentsMenus().subscribe(
      (data) => {
        if (data && date) {
          let contents: Content[] = this.menuUtilsService.getAllContentsOfTheDate(
            date,
            moment,
            data
          );
          console.log(contents);
          this.loadSuggestions(contents);
        }
        this.loading = false;
      },
      (error) => {
        this.error = this.getError(error);
        this.loading = false;
      }
    );
  }

  loadSuggestions(contents: Content[]): void {
    this.suggestions.forEach((value: Content[], key: string) => {
      value = contents.filter((c) => c.typeMeals.find((t) => t === key));
      this.suggestions.set(key, value);
    });
  }

  getOrderDTO(): OrderDTO {
    return new OrderDTO(
      this.order.id,
      this.order.moment,
      this.f.entry.value,
      this.f.dish.value,
      this.f.garnish.value,
      this.order.moment !== "Dîner" ? this.f.dairyProduct.value : null,
      this.f.dessert.value,
      this.order.deliveryDate,
      this.order.orderStatus,
      this.order.patientId
    );
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) return;
    this.updating = true;
    let dto = this.getOrderDTO();
    this.orderService.updateOrder(dto).subscribe(
      (data) => {
        this.updating = false;
        this.toastrService.success(
          "La commande a bien été mise à jour",
          "Mise à jour terminée !"
        );
        this.router.navigate(["/order/all"]);
      },
      (error) => {
        this.updating = false;
        this.toastrService.error(this.getError(error), "Oops !");
      }
    );
  }

  onDelete(): void {
    this.deleting = true;
    this.orderService.deleteOrder(this.order.id).subscribe(
      (data) => {
        this.deleting = false;
        this.toastrService.success(
          "La commande a bien été supprimée",
          "Suppression réussie !"
        );
        this.router.navigate(["/order/all"]);
      },
      (error) => {
        this.deleting = false;
        this.toastrService.error(this.getError(error), "Oops !");
      }
    );
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
