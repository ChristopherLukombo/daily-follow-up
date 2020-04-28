import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { PatientAddComponent } from "./patient-add.component";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { NotifierService } from "angular-notifier";
import { NotifierQueueService } from "angular-notifier/lib/services/notifier-queue.service";

describe("PatientAddComponent", () => {
  let component: PatientAddComponent;
  let fixture: ComponentFixture<PatientAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, FormsModule, HttpClientTestingModule],
      providers: [NotifierService, NotifierQueueService],
      declarations: [PatientAddComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
