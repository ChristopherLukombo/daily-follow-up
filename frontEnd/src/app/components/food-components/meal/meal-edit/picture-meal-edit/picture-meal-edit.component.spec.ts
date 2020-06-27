import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PictureMealEditComponent } from './picture-meal-edit.component';

describe('PictureMealEditComponent', () => {
  let component: PictureMealEditComponent;
  let fixture: ComponentFixture<PictureMealEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PictureMealEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PictureMealEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
