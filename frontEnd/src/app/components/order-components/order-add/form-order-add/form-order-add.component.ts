import { Component, OnInit, Input } from "@angular/core";
import { Patient } from "src/app/models/patient/patient";
import { Content } from "src/app/models/food/content";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { OrderService } from "src/app/services/order/order.service";
import { ToastrService } from "ngx-toastr";
import { Router } from "@angular/router";
import { TypeMessage } from "src/app/models/utils/message-enum";
import { OrderDTO } from "src/app/models/dto/patient/orderDTO";
import { Status } from "src/app/models/utils/status-enum";
import { MenuUtilsService } from "src/app/services/order/menu-utils.service";
import { Menu } from "src/app/models/food/menu";
import { HttpErrorResponse } from "@angular/common/http";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-form-order-add",
  templateUrl: "./form-order-add.component.html",
  styleUrls: ["./form-order-add.component.scss"],
})
export class FormOrderAddComponent implements OnInit {
  @Input() patient: Patient;
  @Input() deliveryDate: string;
  @Input() moment: string;
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
  noMenusForSelectedDate: boolean = false;

  form: FormGroup;
  submitted: boolean = false;
  creating: boolean = false;

  constructor(
    private alimentationService: AlimentationService,
    private menuUtilsService: MenuUtilsService,
    private orderService: OrderService,
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.patient && this.deliveryDate && this.moment) {
      this.createForm();
    }
  }

  ngOnChanges(): void {
    if (this.patient && this.deliveryDate && this.moment) {
      this.getAllContentsOfTheDay(this.deliveryDate, this.moment);
    }
  }

  createForm() {
    const target = {
      deliveryDate: [
        { value: this.deliveryDate, disabled: true },
        Validators.required,
      ],
      moment: [{ value: this.moment, disabled: true }, Validators.required],
      entry: [null, Validators.required],
      dish: [null, Validators.required],
      garnish: [null, Validators.required],
      dairyProduct: [null, Validators.required],
      dessert: [null, Validators.required],
    };
    this.form = this.formBuilder.group(target);
    if (this.moment === "Dîner") {
      this.form.removeControl("dairyProduct");
      this.suggestions.delete("P.L");
    }
  }

  get f() {
    return this.form.controls;
  }

  getAllContentsOfTheDay(date: string, moment: string): void {
    this.loading = true;
    this.noMenusForSelectedDate = false;
    this.alimentationService.getMenusByDate(date).subscribe(
      (data) => {
        if (data && date) {
          let contents: Content[] = this.menuUtilsService.getAllContentsOfTheDate(
            date,
            moment,
            this.filterMenusByStrict(data, this.patient, this.strict)
          );
          this.loadSuggestions(contents);
        } else {
          this.noMenusForSelectedDate = true;
        }
        this.loading = false;
      },
      (error) => {
        this.error = this.getError(error);
        this.loading = false;
      }
    );
  }

  filterMenusByStrict(
    menus: Menu[],
    patient: Patient,
    strict: boolean
  ): Menu[] {
    if (strict === false)
      return this.filterByTexture(menus, this.patient.texture.name);
    let filtered: Menu[] = [];
    menus.forEach((menu) => {
      let found = patient.diets
        .map((d) => d.name)
        .some((diet) => menu.diets.includes(diet));
      if (found) {
        filtered.push(menu);
      }
    });
    return this.filterByTexture(filtered, this.patient.texture.name);
  }

  filterByTexture(menus: Menu[], texture: string): Menu[] {
    return menus.filter((menu) => menu.texture === texture);
  }

  loadSuggestions(contents: Content[]): void {
    this.suggestions.forEach((value: Content[], key: string) => {
      value = contents.filter((c) => c.typeMeals.find((t) => t === key));
      this.suggestions.set(key, value);
    });
  }

  getOrderDTO(): OrderDTO {
    return new OrderDTO(
      null,
      this.moment,
      this.f.entry.value,
      this.f.dish.value,
      this.f.garnish.value,
      this.moment !== "Dîner" ? this.f.dairyProduct.value : null,
      this.f.dessert.value,
      this.deliveryDate,
      Status.VALIDATED,
      null,
      null,
      null,
      null,
      this.patient.id
    );
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) return;
    this.creating = true;
    let dto = this.getOrderDTO();
    this.orderService.createOrder(dto).subscribe(
      (data) => {
        this.creating = false;
        this.toastrService.success(
          "La commande de " +
            this.patient.lastName +
            " " +
            this.patient.firstName +
            " a bien été validé pour le " +
            this.moment +
            " du " +
            this.deliveryDate,
          "En cuisine !"
        );
        this.orderService.removeOrderInfosFromLocal();
        this.router.navigate(["/order/all"]);
      },
      (error) => {
        this.creating = false;
        this.toastrService.error(this.getCustomError(error), "Oops !");
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

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getCustomError(error: HttpErrorResponse): string {
    if (error && error.status === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else if (error && error.status === 500) {
      return error.error.message;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
