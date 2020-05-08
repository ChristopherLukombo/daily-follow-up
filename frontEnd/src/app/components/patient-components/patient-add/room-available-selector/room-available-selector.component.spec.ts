import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { RoomAvailableSelectorComponent } from "./room-available-selector.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { NgxPaginationModule } from "ngx-pagination";
import { SearchPipe } from "src/app/utils/pipes/search.pipe";

describe("RoomAvailableSelectorComponent", () => {
  let component: RoomAvailableSelectorComponent;
  let fixture: ComponentFixture<RoomAvailableSelectorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, NgxPaginationModule],
      declarations: [RoomAvailableSelectorComponent, SearchPipe],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoomAvailableSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
