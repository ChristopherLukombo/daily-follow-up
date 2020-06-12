import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuDeclineComponent } from './menu-decline.component';

describe('MenuDeclineComponent', () => {
  let component: MenuDeclineComponent;
  let fixture: ComponentFixture<MenuDeclineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MenuDeclineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuDeclineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
