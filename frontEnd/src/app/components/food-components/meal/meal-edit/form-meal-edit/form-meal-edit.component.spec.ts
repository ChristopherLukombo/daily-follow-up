import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormMealEditComponent } from './form-meal-edit.component';

describe('FormMealEditComponent', () => {
  let component: FormMealEditComponent;
  let fixture: ComponentFixture<FormMealEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormMealEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormMealEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
