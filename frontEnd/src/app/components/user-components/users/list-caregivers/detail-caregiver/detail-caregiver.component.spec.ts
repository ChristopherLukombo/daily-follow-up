import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailCaregiverComponent } from './detail-caregiver.component';

describe('DetailCaregiverComponent', () => {
  let component: DetailCaregiverComponent;
  let fixture: ComponentFixture<DetailCaregiverComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailCaregiverComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailCaregiverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
