import { DetermineAgePipe } from "./determine-age.pipe";

describe("DetermineAgePipe", () => {
  let pipe: DetermineAgePipe;

  beforeEach(() => {
    pipe = new DetermineAgePipe();
  });

  it("create an instance", () => {
    expect(pipe).toBeTruthy();
  });

  it("providing no value returns nothing", () => {
    const date = "";

    const actual = pipe.transform(date);

    expect(actual).toBeUndefined();
  });

  it("providing birthday returns age", () => {
    const date = "1969-09-10";

    const actual = pipe.transform(date);

    const expected = 50;
    expect(actual).toBe(expected);
  });
});
