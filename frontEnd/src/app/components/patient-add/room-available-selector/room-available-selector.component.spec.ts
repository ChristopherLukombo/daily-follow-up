import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomAvailableSelectorComponent } from './room-available-selector.component';

describe('RoomAvailableSelectorComponent', () => {
  let component: RoomAvailableSelectorComponent;
  let fixture: ComponentFixture<RoomAvailableSelectorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RoomAvailableSelectorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoomAvailableSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
