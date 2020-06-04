import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrentWeeksComponent } from './current-weeks.component';

describe('CurrentWeeksComponent', () => {
  let component: CurrentWeeksComponent;
  let fixture: ComponentFixture<CurrentWeeksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CurrentWeeksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CurrentWeeksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
