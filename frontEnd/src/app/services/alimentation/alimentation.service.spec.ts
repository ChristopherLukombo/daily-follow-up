import { TestBed } from "@angular/core/testing";

import { AlimentationService } from "./alimentation.service";
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe("AlimentationService", () => {
  let service: AlimentationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(AlimentationService);
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });
});
