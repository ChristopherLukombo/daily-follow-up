import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentsDayMenuLockComponent } from './contents-day-menu-lock.component';

describe('ContentsDayMenuLockComponent', () => {
  let component: ContentsDayMenuLockComponent;
  let fixture: ComponentFixture<ContentsDayMenuLockComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContentsDayMenuLockComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentsDayMenuLockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
