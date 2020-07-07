import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormOrderEditComponent } from './form-order-edit.component';

describe('FormOrderEditComponent', () => {
  let component: FormOrderEditComponent;
  let fixture: ComponentFixture<FormOrderEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormOrderEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormOrderEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
