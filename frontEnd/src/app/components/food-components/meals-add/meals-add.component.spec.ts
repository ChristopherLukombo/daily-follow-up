import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { MealsAddComponent } from "./meals-add.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ToastrService, ToastrModule } from "ngx-toastr";
import { RouterTestingModule } from "@angular/router/testing";

describe("MealsAddComponent", () => {
  let component: MealsAddComponent;
  let fixture: ComponentFixture<MealsAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        ToastrModule.forRoot(),
        RouterTestingModule,
      ],
      declarations: [MealsAddComponent],
      providers: [ToastrService],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MealsAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
