import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ListPatientsComponent } from "./list-patients.component";
import { NgxPaginationModule } from "ngx-pagination";
import { SearchPipe } from "src/app/utils/pipes/search.pipe";

describe("ListPatientsComponent", () => {
  let component: ListPatientsComponent;
  let fixture: ComponentFixture<ListPatientsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [NgxPaginationModule],
      declarations: [ListPatientsComponent, SearchPipe],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListPatientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
