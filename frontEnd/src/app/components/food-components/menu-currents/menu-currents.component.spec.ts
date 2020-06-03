import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuCurrentsComponent } from './menu-currents.component';

describe('MenuCurrentsComponent', () => {
  let component: MenuCurrentsComponent;
  let fixture: ComponentFixture<MenuCurrentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MenuCurrentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuCurrentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
