import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormPatientAddComponent } from './form-patient-add.component';

describe('FormPatientAddComponent', () => {
  let component: FormPatientAddComponent;
  let fixture: ComponentFixture<FormPatientAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormPatientAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormPatientAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
