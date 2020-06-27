import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientsByStatusComponent } from './patients-by-status.component';

describe('PatientsByStatusComponent', () => {
  let component: PatientsByStatusComponent;
  let fixture: ComponentFixture<PatientsByStatusComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatientsByStatusComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientsByStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
