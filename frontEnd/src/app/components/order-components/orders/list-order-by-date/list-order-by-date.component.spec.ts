import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListOrderByDateComponent } from './list-order-by-date.component';

describe('ListOrderByDateComponent', () => {
  let component: ListOrderByDateComponent;
  let fixture: ComponentFixture<ListOrderByDateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListOrderByDateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListOrderByDateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
