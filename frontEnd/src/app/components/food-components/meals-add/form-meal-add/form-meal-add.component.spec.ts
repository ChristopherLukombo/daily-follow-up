import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormMealAddComponent } from './form-meal-add.component';

describe('FormMealAddComponent', () => {
  let component: FormMealAddComponent;
  let fixture: ComponentFixture<FormMealAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormMealAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormMealAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
