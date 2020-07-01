import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientsPerAllergyComponent } from './patients-per-allergy.component';

describe('PatientsPerAllergyComponent', () => {
  let component: PatientsPerAllergyComponent;
  let fixture: ComponentFixture<PatientsPerAllergyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatientsPerAllergyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientsPerAllergyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
