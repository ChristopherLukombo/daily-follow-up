import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ListRoomsAvailableComponent } from "./list-rooms-available.component";
import { NgxPaginationModule } from "ngx-pagination";
import { SearchPipe } from "src/app/utils/pipes/search.pipe";

describe("ListRoomsAvailableComponent", () => {
  let component: ListRoomsAvailableComponent;
  let fixture: ComponentFixture<ListRoomsAvailableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [NgxPaginationModule],
      declarations: [ListRoomsAvailableComponent, SearchPipe],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListRoomsAvailableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
