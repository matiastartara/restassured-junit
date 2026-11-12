# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A REST Assured + JUnit 6 API test project (Java 17, Maven) that runs against the public JSONPlaceholder API (`https://jsonplaceholder.typicode.com`). There is no application code: everything lives under `src/test/`, and `src/main/` is empty.

## Commands

There is no Maven wrapper, so use the system `mvn`.

```bash
mvn -B test                                     # all tests (what CI runs by default)
mvn -B test -Dtest=SmokeSuite                   # smoke suite only (CI "smoke" option)
mvn -B test -Dtest=GetPostsTest                 # one class
mvn -B test -Dtest=GetPostsTest#getPostsWithLimit   # one method
mvn -B test -Dgroups=regression                 # by JUnit tag
```

There is no linter or formatter configured. Surefire reports go to `target/surefire-reports/`.

## Structure and conventions

- **Packages are top-level and unprefixed:** `test`, `model`, `util` and `suite`, not `org.example.*`.
- **`test.BaseTest`**: every test class extends it. Its `@BeforeAll` sets `RestAssured.baseURI` and enables request/response logging on validation failure. Use relative paths (`/posts`) in tests and never hardcode the host.
- **Tags drive suites:** every test is tagged `smoke` or `regression`. `suite.SmokeSuite` selects package `test` with `@IncludeTags("smoke")`. The suite class name doesn't match Surefire's default `*Test` pattern, so a plain `mvn test` runs each test class once and does not run the suite again.
- **Test shape:** `LoggerUtil.info("Requesting <METHOD> <path>")` → `given()…when()…then()` with Hamcrest assertions in `.body(...)` → `.extract().response()` → log status, time and pretty body. Use JUnit `assertEquals` only for checks that span multiple responses or deserialized objects.
- **Classes are grouped by resource and operation** (`GetPostsTest`, `CreatePostTest`, `GetUsersTest`). Each test has a `@DisplayName` like `"GET /posts/1 matches the post JSON schema"`.
- **POJOs** in `model` are Java records that Jackson maps by component name, e.g. `.extract().as(Todo[].class)`.
- **JSON schemas** live in `src/test/resources/schemas/` and are used via `matchesJsonSchemaInClasspath("schemas/<name>.json")`. They use draft-07 with `additionalProperties: false`.
- **Logging** goes through `util.LoggerUtil`, a thin `java.util.logging` wrapper. Don't add a logging framework.

## CI

`.github/workflows/ci.yml` runs on pushes and PRs to `main`, and on manual `workflow_dispatch` with a `suite` choice (`all` / `smoke`). It uses JDK 17 (Temurin) and uploads the Surefire reports as an artifact. Pushes to other branches don't trigger it.

Because the tests hit a live public API, failures can come from the network or the external service rather than from the code.
