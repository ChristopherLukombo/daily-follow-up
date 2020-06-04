import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ListCaregiversComponent } from "./list-caregivers.component";

describe("ListCaregiversComponent", () => {
  let component: ListCaregiversComponent;
  let fixture: ComponentFixture<ListCaregiversComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ListCaregiversComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListCaregiversComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
