import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormOrderAddComponent } from './form-order-add.component';

describe('FormOrderAddComponent', () => {
  let component: FormOrderAddComponent;
  let fixture: ComponentFixture<FormOrderAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormOrderAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormOrderAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
