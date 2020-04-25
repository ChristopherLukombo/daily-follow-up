import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ModalDangerComponent } from "./modal-danger.component";

describe("ModalComponent", () => {
  let component: ModalDangerComponent;
  let fixture: ComponentFixture<ModalDangerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ModalDangerComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalDangerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
