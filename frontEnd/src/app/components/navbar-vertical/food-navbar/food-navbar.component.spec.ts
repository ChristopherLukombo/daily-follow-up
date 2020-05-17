import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FoodNavbarComponent } from './food-navbar.component';

describe('FoodNavbarComponent', () => {
  let component: FoodNavbarComponent;
  let fixture: ComponentFixture<FoodNavbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FoodNavbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FoodNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
