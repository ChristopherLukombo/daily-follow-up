import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentsDayMenuEditComponent } from './contents-day-menu-edit.component';

describe('ContentsDayMenuEditComponent', () => {
  let component: ContentsDayMenuEditComponent;
  let fixture: ComponentFixture<ContentsDayMenuEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContentsDayMenuEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentsDayMenuEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
