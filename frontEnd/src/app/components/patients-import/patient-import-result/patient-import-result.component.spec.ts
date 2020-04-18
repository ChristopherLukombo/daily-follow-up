import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientImportResultComponent } from './patient-import-result.component';

describe('PatientImportResultComponent', () => {
  let component: PatientImportResultComponent;
  let fixture: ComponentFixture<PatientImportResultComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatientImportResultComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientImportResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
