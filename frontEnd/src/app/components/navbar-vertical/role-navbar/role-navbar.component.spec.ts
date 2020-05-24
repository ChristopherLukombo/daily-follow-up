import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleNavbarComponent } from './role-navbar.component';

describe('RoleNavbarComponent', () => {
  let component: RoleNavbarComponent;
  let fixture: ComponentFixture<RoleNavbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RoleNavbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
