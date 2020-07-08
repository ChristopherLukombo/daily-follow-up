import { TestBed } from '@angular/core/testing';

import { MenuUtilsService } from './menu-utils.service';

describe('MenuUtilsService', () => {
  let service: MenuUtilsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MenuUtilsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
