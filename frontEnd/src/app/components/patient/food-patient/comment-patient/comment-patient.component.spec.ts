import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { CommentPatientComponent } from "./comment-patient.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe("CommentPatientComponent", () => {
  let component: CommentPatientComponent;
  let fixture: ComponentFixture<CommentPatientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CommentPatientComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentPatientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
