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

  form: FormGroup;
  submitted: boolean = false;
  creating: boolean = false;

  constructor(
    private alimentationService: AlimentationService,
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
    this.getAllContentsAvailable();
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

  // TODO : Gerer les menus strict ou non
  getAllContentsAvailable(): void {
    this.loading = true;
    this.alimentationService.getAllContents().subscribe(
      (data) => {
        if (data) {
          this.loadSuggestions(data);
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
      null,
      this.moment,
      this.f.entry.value,
      this.f.dish.value,
      this.f.garnish.value,
      this.moment !== "Dîner" ? this.f.dairyProduct.value : null,
      this.f.dessert.value,
      this.deliveryDate,
      Status.VALIDATED,
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
          "La commande a bien été créé",
          "Création terminée !"
        );
        this.router.navigate(["/order/all"]);
      },
      (error) => {
        this.creating = false;
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
