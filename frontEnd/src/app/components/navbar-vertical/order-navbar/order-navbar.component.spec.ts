import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderNavbarComponent } from './order-navbar.component';

describe('OrderNavbarComponent', () => {
  let component: OrderNavbarComponent;
  let fixture: ComponentFixture<OrderNavbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderNavbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
