import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  ElementRef,
} from "@angular/core";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "typeahead",
  template: `<div *ngIf="opened" class="custom-typeahead">
    <option *ngFor="let item of list" (click)="onSelect(item)">
      {{ getFieldValue(item) }}
    </option>
  </div>`,
  styleUrls: ["./typeahead.component.scss"],
  host: {
    "(document:click)": "onClick($event)",
  },
})
export class Typeahead implements OnInit {
  @Input() list: Object[] = [];
  @Input() field: string = null;
  @Output() select = new EventEmitter<Object>();

  opened: boolean = false;

  constructor(private _eref: ElementRef) {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    this.opened = this.list.length > 0 ? true : false;
  }

  onClick(event): void {
    if (!this._eref.nativeElement.contains(event.target)) {
      this.opened = false;
    }
  }

  getFieldValue(object: Object): string {
    if (this.field) return object[this.field];
  }

  onSelect(item: Object): void {
    this.select.emit(item);
    this.opened = false;
  }
}
