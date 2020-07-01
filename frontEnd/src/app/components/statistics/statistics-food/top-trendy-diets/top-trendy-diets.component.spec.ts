import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopTrendyDietsComponent } from './top-trendy-diets.component';

describe('TopTrendyDietsComponent', () => {
  let component: TopTrendyDietsComponent;
  let fixture: ComponentFixture<TopTrendyDietsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopTrendyDietsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopTrendyDietsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
