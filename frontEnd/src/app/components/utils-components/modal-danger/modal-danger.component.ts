import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { faTrashAlt } from "@fortawesome/free-solid-svg-icons";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-modal-danger",
  templateUrl: "./modal-danger.component.html",
  styleUrls: ["./modal-danger.component.scss"],
})
export class ModalDangerComponent implements OnInit {
  deleteLogo = faTrashAlt;

  @Input() btnTitle: string;
  @Input() header: string = "Êtes-vous sur ?";
  @Input() text: string;
  @Output() confirmation = new EventEmitter<boolean>();

  constructor() {}

  ngOnInit(): void {}

  confirm(): void {
    this.confirmation.emit();
  }
}
