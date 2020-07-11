import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentsOfTodayComponent } from './contents-of-today.component';

describe('ContentsOfTodayComponent', () => {
  let component: ContentsOfTodayComponent;
  let fixture: ComponentFixture<ContentsOfTodayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContentsOfTodayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentsOfTodayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
