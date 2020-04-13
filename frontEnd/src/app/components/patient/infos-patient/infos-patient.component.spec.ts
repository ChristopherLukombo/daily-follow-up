import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { InfosPatientComponent } from "./infos-patient.component";
import { DetermineAgePipe } from "src/app/utils/pipes/determine-age.pipe";
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe("InfosPatientComponent", () => {
  let component: InfosPatientComponent;
  let fixture: ComponentFixture<InfosPatientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [InfosPatientComponent, DetermineAgePipe],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InfosPatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
