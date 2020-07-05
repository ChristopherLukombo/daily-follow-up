import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatisticsFoodComponent } from './statistics-food.component';

describe('StatisticsFoodComponent', () => {
  let component: StatisticsFoodComponent;
  let fixture: ComponentFixture<StatisticsFoodComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatisticsFoodComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatisticsFoodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
