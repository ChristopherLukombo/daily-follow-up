import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReplacementsCardComponent } from './replacements-card.component';

describe('ReplacementsCardComponent', () => {
  let component: ReplacementsCardComponent;
  let fixture: ComponentFixture<ReplacementsCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReplacementsCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReplacementsCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
