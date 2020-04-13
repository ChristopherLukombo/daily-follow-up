import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { FoodPatientComponent } from "./food-patient.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe("FoodPatientComponent", () => {
  let component: FoodPatientComponent;
  let fixture: ComponentFixture<FoodPatientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [FoodPatientComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FoodPatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
