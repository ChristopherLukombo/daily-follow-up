import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentsDayMenuComponent } from './contents-day-menu.component';

describe('ContentsDayMenuComponent', () => {
  let component: ContentsDayMenuComponent;
  let fixture: ComponentFixture<ContentsDayMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContentsDayMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentsDayMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
