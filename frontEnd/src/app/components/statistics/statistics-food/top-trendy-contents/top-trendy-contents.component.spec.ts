import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopTrendyContentsComponent } from './top-trendy-contents.component';

describe('TopTrendyContentsComponent', () => {
  let component: TopTrendyContentsComponent;
  let fixture: ComponentFixture<TopTrendyContentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopTrendyContentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopTrendyContentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
