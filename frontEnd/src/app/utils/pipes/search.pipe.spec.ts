import { SearchPipe, HighLightPipe } from "./search.pipe";

describe("SearchPipe", () => {
  let pipe: SearchPipe;

  beforeEach(() => {
    pipe = new SearchPipe();
  });

  it("create an instance", () => {
    expect(pipe).toBeTruthy();
  });

  it("providing items, property to search but no search returns items", () => {
    const items = [
      { firstProperty: "aRandomValue", secondProperty: "anotherRandomValue" },
      { firstProperty: "anotherOne", secondProperty: "RandomValueAgain" },
    ];
    const value = "secondProperty";
    const search = "";

    const actual = pipe.transform(items, value, search);

    const expected = items;
    expect(actual).toEqual(expected);
  });

  it("providing items, property to search and the search returns same results", () => {
    const items = [
      { firstProperty: "aRandomValue", secondProperty: "anotherRandomValue" },
      { firstProperty: "anotherOne", secondProperty: "RandomValueAgain" },
    ];
    const value = "secondProperty";
    const search = "random";

    const actual = pipe.transform(items, value, search);

    const expected = items;
    expect(actual).toEqual(expected);
  });

  it("providing items, property to search and the search returns many results", () => {
    const firstItem = {
      firstProperty: "aRandomValue",
      secondProperty: "anotherRandomValue",
    };
    const secondItem = {
      firstProperty: "anotherOne",
      secondProperty: "RandomValueAgain",
    };
    const thirdItem = {
      firstProperty: "again",
      secondProperty: "shouldBeNotIncludedInResult",
    };
    const items = [firstItem, secondItem, thirdItem];
    const value = "secondProperty";
    const search = "random";

    const actual = pipe.transform(items, value, search);

    const expected = [firstItem, secondItem];
    expect(actual).toEqual(expected);
  });

  it("providing items, property to search and the search returns one result", () => {
    const firstItem = {
      firstProperty: "aRandomValue",
      secondProperty: "anotherRandomValue",
    };
    const secondItem = {
      firstProperty: "anotherOne",
      secondProperty: "RandomValueAgain",
    };
    const items = [firstItem, secondItem];
    const value = "secondProperty";
    const search = "anotherRandomValue";

    const actual = pipe.transform(items, value, search);

    const expected = [firstItem];
    expect(actual).toEqual(expected);
  });
});

describe("HightLightPipe", () => {
  let pipe: HighLightPipe;

  beforeEach(() => {
    pipe = new HighLightPipe();
  });

  it("create an instance", () => {
    expect(pipe).toBeTruthy();
  });

  it("providing value but no args returns value", () => {
    const value = "This is an awesome unit test of the pipe hightlight";
    const args = "";

    const actual = pipe.transform(value, args);

    const expected = value;
    expect(actual).toEqual(expected);
  });

  it("providing value but with args returns value with highlighted args", () => {
    const value = "This is an awesome unit test of the pipe hightlight";
    const args = "an awesome unit test";

    const actual = pipe.transform(value, args);

    const expected = value.replace(
      args,
      `<span class="highlight">${args}</span>`
    );
    expect(actual).toEqual(expected);
  });
});
