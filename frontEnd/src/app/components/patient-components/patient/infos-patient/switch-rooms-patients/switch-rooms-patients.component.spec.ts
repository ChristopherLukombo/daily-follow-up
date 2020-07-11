import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SwitchRoomsPatientsComponent } from './switch-rooms-patients.component';

describe('SwitchRoomsPatientsComponent', () => {
  let component: SwitchRoomsPatientsComponent;
  let fixture: ComponentFixture<SwitchRoomsPatientsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SwitchRoomsPatientsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SwitchRoomsPatientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
