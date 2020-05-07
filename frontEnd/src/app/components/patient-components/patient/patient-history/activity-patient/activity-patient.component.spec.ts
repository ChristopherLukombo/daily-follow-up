import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ActivityPatientComponent } from "./activity-patient.component";

describe("ActivityPatientComponent", () => {
  let component: ActivityPatientComponent;
  let fixture: ComponentFixture<ActivityPatientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ActivityPatientComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivityPatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
