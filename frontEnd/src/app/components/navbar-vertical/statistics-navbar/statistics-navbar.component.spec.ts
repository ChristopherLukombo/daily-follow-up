import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatisticsNavbarComponent } from './statistics-navbar.component';

describe('StatisticsNavbarComponent', () => {
  let component: StatisticsNavbarComponent;
  let fixture: ComponentFixture<StatisticsNavbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatisticsNavbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatisticsNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
