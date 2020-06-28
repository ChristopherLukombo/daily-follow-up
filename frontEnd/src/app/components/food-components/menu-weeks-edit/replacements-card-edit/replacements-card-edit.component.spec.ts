import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReplacementsCardEditComponent } from './replacements-card-edit.component';

describe('ReplacementsCardEditComponent', () => {
  let component: ReplacementsCardEditComponent;
  let fixture: ComponentFixture<ReplacementsCardEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReplacementsCardEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReplacementsCardEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
