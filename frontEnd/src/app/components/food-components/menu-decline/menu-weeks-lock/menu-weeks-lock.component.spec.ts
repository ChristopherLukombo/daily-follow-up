import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuWeeksLockComponent } from './menu-weeks-lock.component';

describe('MenuWeeksLockComponent', () => {
  let component: MenuWeeksLockComponent;
  let fixture: ComponentFixture<MenuWeeksLockComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MenuWeeksLockComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuWeeksLockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
