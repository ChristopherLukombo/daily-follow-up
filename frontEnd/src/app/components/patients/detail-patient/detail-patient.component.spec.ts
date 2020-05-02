import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { DetailPatientComponent } from "./detail-patient.component";
import { DetermineAgePipe } from "src/app/utils/pipes/number-utils.pipe";

describe("DetailPatientComponent", () => {
  let component: DetailPatientComponent;
  let fixture: ComponentFixture<DetailPatientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DetailPatientComponent, DetermineAgePipe],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailPatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
