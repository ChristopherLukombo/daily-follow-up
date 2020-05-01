import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { PatientsImportComponent } from "./patients-import.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ToastrService, ToastrModule } from "ngx-toastr";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

describe("PatientsImportComponent", () => {
  let component: PatientsImportComponent;
  let fixture: ComponentFixture<PatientsImportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        BrowserAnimationsModule,
        ToastrModule.forRoot(),
        ReactiveFormsModule,
        FormsModule,
        HttpClientTestingModule,
      ],
      declarations: [PatientsImportComponent],
      providers: [ToastrService],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientsImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  describe("#handleFile", () => {
    it("should not handle file if input is not valid", () => {
      const files = { 0: null, length: 1, item: (i: number) => files[i] };
      component.handleFile(files);
      expect(component.file).toBeUndefined();
    });
    it("should not handle file if input is not valid csv", () => {
      const files = {
        0: new File(["file"], "file.pdf", { type: "application/pdf" }),
        length: 1,
        item: (i: number) => files[i],
      };
      component.handleFile(files);
      expect(component.file).toBeUndefined();
    });
    it("should handle file if input is valid csv", () => {
      const files = {
        0: new File(["file"], "file.csv", { type: "text/csv" }),
        length: 1,
        item: (i: number) => files[i],
      };
      const expected = new File(["file"], "file.csv", { type: "text/csv" });
      component.handleFile(files);
      expect(component.file).toEqual(expected);
    });
  });

  describe("#validExtension", () => {
    it("file is not a csv file", () => {
      const file = new File(["file"], "file.pdf", { type: "application/pdf" });
      const expected = false;
      const actual = component.validExtension(file);
      expect(actual).toBe(expected);
    });
    it("file is not a csv mime type", () => {
      const file = new File(["file"], "file.csv", { type: "application/csv" });
      const expected = false;
      const actual = component.validExtension(file);
      expect(actual).toBe(expected);
    });
    it("file is a csv mime type", () => {
      const fileList = {
        0: new File(["file"], "file.csv", { type: "application/vnd.ms-excel" }),
        1: new File(["file"], "file.csv", { type: "text/csv" }),
        2: new File(["file"], "file.csv", { type: "text/plain" }),
      };
      const expected = true;
      const actualFirst = component.validExtension(fileList[0]);
      const actualSecond = component.validExtension(fileList[1]);
      const actualThird = component.validExtension(fileList[2]);
      expect(actualFirst).toBe(expected);
      expect(actualSecond).toBe(expected);
      expect(actualThird).toBe(expected);
    });
  });
});
