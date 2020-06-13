import { TestBed } from "@angular/core/testing";

import { DeclinatorService } from "./declinator.service";

describe("DeclinatorServiceService", () => {
  let service: DeclinatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeclinatorService);
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });
});
