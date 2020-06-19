import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuWeeksEditComponent } from './menu-weeks-edit.component';

describe('MenuWeeksEditComponent', () => {
  let component: MenuWeeksEditComponent;
  let fixture: ComponentFixture<MenuWeeksEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MenuWeeksEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuWeeksEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
