import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatisticsPatientsComponent } from './statistics-patients.component';

describe('StatisticsPatientsComponent', () => {
  let component: StatisticsPatientsComponent;
  let fixture: ComponentFixture<StatisticsPatientsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatisticsPatientsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatisticsPatientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
