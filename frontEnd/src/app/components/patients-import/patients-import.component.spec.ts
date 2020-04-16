import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientsImportComponent } from './patients-import.component';

describe('PatientsImportComponent', () => {
  let component: PatientsImportComponent;
  let fixture: ComponentFixture<PatientsImportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatientsImportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientsImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
