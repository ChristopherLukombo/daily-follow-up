import { TestBed } from '@angular/core/testing';

import { ArtificialIntelligenceService } from './artificial-intelligence.service';

describe('ArtificialIntelligenceService', () => {
  let service: ArtificialIntelligenceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ArtificialIntelligenceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
