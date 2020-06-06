import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailFloorComponent } from './detail-floor.component';

describe('DetailFloorComponent', () => {
  let component: DetailFloorComponent;
  let fixture: ComponentFixture<DetailFloorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailFloorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailFloorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
