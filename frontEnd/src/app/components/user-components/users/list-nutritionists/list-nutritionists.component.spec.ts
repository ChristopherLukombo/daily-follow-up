import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListNutritionistsComponent } from './list-nutritionists.component';

describe('ListNutritionistsComponent', () => {
  let component: ListNutritionistsComponent;
  let fixture: ComponentFixture<ListNutritionistsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListNutritionistsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListNutritionistsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
