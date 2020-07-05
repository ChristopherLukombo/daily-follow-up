import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuAllComponent } from './menu-all.component';

describe('MenuAllComponent', () => {
  let component: MenuAllComponent;
  let fixture: ComponentFixture<MenuAllComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MenuAllComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuAllComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
