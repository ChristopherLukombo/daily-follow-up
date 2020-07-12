import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InfosFloorComponent } from './infos-floor.component';

describe('InfosFloorComponent', () => {
  let component: InfosFloorComponent;
  let fixture: ComponentFixture<InfosFloorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InfosFloorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InfosFloorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
