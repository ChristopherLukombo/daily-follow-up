import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InfosMealComponent } from './infos-meal.component';

describe('InfosMealComponent', () => {
  let component: InfosMealComponent;
  let fixture: ComponentFixture<InfosMealComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InfosMealComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InfosMealComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
