import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientsImportInfosComponent } from './patients-import-infos.component';

describe('PatientsImportInfosComponent', () => {
  let component: PatientsImportInfosComponent;
  let fixture: ComponentFixture<PatientsImportInfosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatientsImportInfosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientsImportInfosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
