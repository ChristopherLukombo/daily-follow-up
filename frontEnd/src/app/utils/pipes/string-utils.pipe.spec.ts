import { getInitialsPipe, getActionPatientPipe } from "./string-utils.pipe";

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

describe("getActionPatientPipe", () => {
  let pipe: getActionPatientPipe;

  beforeEach(() => {
    pipe = new getActionPatientPipe();
  });

  it("create an instance", () => {
    expect(pipe).toBeTruthy();
  });

  it("providing no action returns nothing", () => {
    const action = "";

    const actual = pipe.transform(action);

    expect(actual).toBeUndefined();
  });

  it("providing action returns corrects sentence", () => {
    const first = "INSERTED";
    const second = "UPDATED";
    const third = "DELETED";
    const fourth = "RECREATED";

    const actualFirst = pipe.transform(first);
    const actualSecond = pipe.transform(second);
    const actualThird = pipe.transform(third);
    const actualFourth = pipe.transform(fourth);

    const expectedFirst = "Arrivée du patient";
    expect(actualFirst).toBe(expectedFirst);
    const expectedSecond = "Modification du patient";
    expect(actualSecond).toBe(expectedSecond);
    const expectedThird = "Le patient a quitté la clinique";
    expect(actualThird).toBe(expectedThird);
    const expectedFourth = "Le patient est revenu dans la clinique";
    expect(actualFourth).toBe(expectedFourth);
  });
});
