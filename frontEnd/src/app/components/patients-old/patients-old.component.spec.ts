import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientsOldComponent } from './patients-old.component';

describe('PatientsOldComponent', () => {
  let component: PatientsOldComponent;
  let fixture: ComponentFixture<PatientsOldComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatientsOldComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientsOldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
