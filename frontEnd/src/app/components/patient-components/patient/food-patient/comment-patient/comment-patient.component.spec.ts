import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { CommentPatientComponent } from "./comment-patient.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ToastrModule, ToastrService } from "ngx-toastr";

describe("CommentPatientComponent", () => {
  let component: CommentPatientComponent;
  let fixture: ComponentFixture<CommentPatientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        BrowserAnimationsModule,
        ToastrModule.forRoot(),
      ],
      declarations: [CommentPatientComponent],
      providers: [ToastrService],
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
