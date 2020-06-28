import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuDeclinedEditComponent } from './menu-declined-edit.component';

describe('MenuDeclinedEditComponent', () => {
  let component: MenuDeclinedEditComponent;
  let fixture: ComponentFixture<MenuDeclinedEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MenuDeclinedEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuDeclinedEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
