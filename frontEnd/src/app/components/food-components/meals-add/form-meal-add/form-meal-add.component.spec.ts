import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { FormMealAddComponent } from "./form-meal-add.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe("FormMealAddComponent", () => {
  let component: FormMealAddComponent;
  let fixture: ComponentFixture<FormMealAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, FormsModule, HttpClientTestingModule],
      declarations: [FormMealAddComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormMealAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
