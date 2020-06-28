import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiffDeclinedContentDayComponent } from './diff-declined-content-day.component';

describe('DiffDeclinedContentDayComponent', () => {
  let component: DiffDeclinedContentDayComponent;
  let fixture: ComponentFixture<DiffDeclinedContentDayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiffDeclinedContentDayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiffDeclinedContentDayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
