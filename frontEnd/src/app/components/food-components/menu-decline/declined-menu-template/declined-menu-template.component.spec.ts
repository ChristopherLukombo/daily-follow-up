import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeclinedMenuTemplateComponent } from './declined-menu-template.component';

describe('DeclinedMenuTemplateComponent', () => {
  let component: DeclinedMenuTemplateComponent;
  let fixture: ComponentFixture<DeclinedMenuTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeclinedMenuTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeclinedMenuTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
