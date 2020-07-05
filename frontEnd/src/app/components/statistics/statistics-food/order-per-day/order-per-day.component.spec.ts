import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderPerDayComponent } from './order-per-day.component';

describe('OrderPerDayComponent', () => {
  let component: OrderPerDayComponent;
  let fixture: ComponentFixture<OrderPerDayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderPerDayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderPerDayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
