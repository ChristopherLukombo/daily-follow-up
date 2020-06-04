import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuWeeksComponent } from './menu-weeks.component';

describe('MenuWeeksComponent', () => {
  let component: MenuWeeksComponent;
  let fixture: ComponentFixture<MenuWeeksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MenuWeeksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuWeeksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
