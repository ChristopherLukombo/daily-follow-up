import { getInitialsPipe } from "./string-utils.pipe";

describe("getInitialsPipe", () => {
  it("create an instance", () => {
    const pipe = new getInitialsPipe();
    expect(pipe).toBeTruthy();
  });
});
