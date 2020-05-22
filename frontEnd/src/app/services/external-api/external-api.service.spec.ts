import { TestBed } from "@angular/core/testing";

import { ExternalApiService } from "./external-api.service";
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe("ExternalApiService", () => {
  let service: ExternalApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(ExternalApiService);
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });
});
