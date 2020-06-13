import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReplacementsCardLockComponent } from './replacements-card-lock.component';

describe('ReplacementsCardLockComponent', () => {
  let component: ReplacementsCardLockComponent;
  let fixture: ComponentFixture<ReplacementsCardLockComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReplacementsCardLockComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReplacementsCardLockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
