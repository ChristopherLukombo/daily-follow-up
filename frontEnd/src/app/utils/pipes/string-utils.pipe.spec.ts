import { getInitialsPipe } from "./string-utils.pipe";

describe("getInitialsPipe", () => {
  let pipe: getInitialsPipe;

  beforeEach(() => {
    pipe = new getInitialsPipe();
  });

  it("create an instance", () => {
    expect(pipe).toBeTruthy();
  });

  it("providing no pseudo returns nothing", () => {
    const pseudo = "";

    const actual = pipe.transform(pseudo);

    expect(actual).toBeUndefined();
  });

  it("providing pseudo returns corrects initials", () => {
    const first = "deliessche_a";
    const second = "lukombo_c";
    const third = "kemissi_n";

    const actualFirst = pipe.transform(first);
    const actualSecond = pipe.transform(second);
    const actualThird = pipe.transform(third);

    const expectedFirst = "AD";
    expect(actualFirst).toBe(expectedFirst);
    const expectedSecond = "CL";
    expect(actualSecond).toBe(expectedSecond);
    const expectedThird = "NK";
    expect(actualThird).toBe(expectedThird);
  });
});
