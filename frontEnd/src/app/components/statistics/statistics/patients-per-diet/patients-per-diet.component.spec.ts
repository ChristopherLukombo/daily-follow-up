import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientsPerDietComponent } from './patients-per-diet.component';

describe('PatientsPerDietComponent', () => {
  let component: PatientsPerDietComponent;
  let fixture: ComponentFixture<PatientsPerDietComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatientsPerDietComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientsPerDietComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
