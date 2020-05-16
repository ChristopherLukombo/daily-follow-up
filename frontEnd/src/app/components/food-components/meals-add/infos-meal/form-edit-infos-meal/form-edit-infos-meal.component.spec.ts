import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormEditInfosMealComponent } from './form-edit-infos-meal.component';

describe('FormEditInfosMealComponent', () => {
  let component: FormEditInfosMealComponent;
  let fixture: ComponentFixture<FormEditInfosMealComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormEditInfosMealComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormEditInfosMealComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
