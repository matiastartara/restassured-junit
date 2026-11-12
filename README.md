<div align="center">

# 🚀 REST Assured + JUnit API Tests

**Automated API tests for [JSONPlaceholder](https://jsonplaceholder.typicode.com), written with REST Assured and JUnit.** ✨

[![API Tests](https://github.com/matiastartara/restassured-junit/actions/workflows/ci.yml/badge.svg)](https://github.com/matiastartara/restassured-junit/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.6.3+-C71A36?logo=apachemaven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-6-25A162?logo=junit5&logoColor=white)
![REST Assured](https://img.shields.io/badge/REST%20Assured-6.2.0-6DB33F)

</div>

---

## 📚 Table of contents

- [✨ What's inside](#-whats-inside)
- [🧰 Requirements](#-requirements)
- [📥 Clone](#-clone)
- [📦 Install](#-install)
- [▶️ Run the tests](#️-run-the-tests)
- [📊 Test reports](#-test-reports)
- [🤖 CI with GitHub Actions](#-ci-with-github-actions)
- [🗂️ Project structure](#️-project-structure)
- [🏷️ Tags](#️-tags)

---

## ✨ What's inside

| | Feature |
|---|---|
| 🔍 | `GET` tests with Hamcrest assertions on status, body, and sizes |
| 📝 | `POST` test that creates a resource |
| 🧩 | Deserialization of responses into Java records (`Todo`) |
| 📐 | JSON Schema validation (`schemas/post.json`) |
| 🔁 | Consistency checks between equivalent endpoints |
| ⏱️ | Response-time check |
| 💨 | Smoke suite powered by JUnit tags |
| 🤖 | CI on GitHub Actions with downloadable reports |

---

## 🧰 Requirements

| Tool | Version | Check with |
|---|---|---|
| ☕ Java JDK | **17** or newer | `java -version` |
| 📦 Apache Maven | **3.6.3** or newer | `mvn -version` |
| 🐙 Git | any recent version | `git --version` |
| 🌐 Internet connection | — | the tests hit a live public API |

> 💡 **Tip:** On macOS you can install everything with Homebrew:
> ```bash
> brew install openjdk@17 maven git
> ```

---

## 📥 Clone

```bash
git clone https://github.com/matiastartara/restassured-junit.git
cd restassured-junit
```

---

## 📦 Install

Download the dependencies and compile the tests without running them:

```bash
mvn -B test-compile
```

✅ If you see `BUILD SUCCESS`, you're ready to go!

---

## ▶️ Run the tests

| What | Command |
|---|---|
| 🧪 All tests | `mvn test` |
| 💨 Smoke suite only | `mvn test -Dtest=SmokeSuite` |
| 🏷️ By tag | `mvn test -Dgroups=regression` |
| 📄 A single class | `mvn test -Dtest=GetPostsTest` |
| 🎯 A single test | `mvn test -Dtest=GetPostsTest#getPostsWithLimit` |

Each test logs the request it makes and the response it gets back (status, time, and body), so you can follow along in the console 👀:

```
14:02:11.384 [INFO] Requesting GET /posts?_limit=5
14:02:11.902 [INFO] Response (200, 512 ms):
[ { "userId": 1, "id": 1, ... } ]
```

---

## 📊 Test reports

### 💻 Locally

After a run, Surefire writes its reports to:

```
target/surefire-reports/
├── TEST-test.GetPostsTest.xml   ← JUnit XML (for CI tools / IDEs)
└── test.GetPostsTest.txt        ← plain-text summary
```

```bash
open target/surefire-reports      # macOS
xdg-open target/surefire-reports  # Linux
```

### ☁️ From GitHub Actions

Every CI run uploads the reports as an artifact called **`surefire-reports`**, even when tests fail.

**From the browser 🖱️**

1. Go to the **Actions** tab of the repo.
2. Open the run you're interested in.
3. Scroll down to **Artifacts**.
4. Click **`surefire-reports`** to download the `.zip`. 📦

**From the terminal ⌨️** (requires the [GitHub CLI](https://cli.github.com/))

```bash
gh run list --workflow=ci.yml          # find the run ID
gh run download <run-id> -n surefire-reports
```

---

## 🤖 CI with GitHub Actions

The [`API Tests`](.github/workflows/ci.yml) workflow runs:

- 🔀 on every **push** to `main`
- 📬 on every **pull request** to `main`
- 🖐️ **manually**, from *Actions → API Tests → Run workflow*, where you can pick:
  - `all` → the whole test suite
  - `smoke` → only the tests tagged `smoke`

---

## 🗂️ Project structure

```
src/test/
├── java/
│   ├── test/      🧪 test classes (all extend BaseTest)
│   ├── model/     🧩 POJOs / records for deserialization
│   ├── suite/     💨 JUnit suites (SmokeSuite)
│   └── util/      🛠️ LoggerUtil
└── resources/
    └── schemas/   📐 JSON Schemas
```

`BaseTest` sets the API base URL (`https://jsonplaceholder.typicode.com`), so the tests only use relative paths like `/posts`.

---

## 🏷️ Tags

| Tag | Purpose |
|---|---|
| 💨 `smoke` | Fast, critical checks: is the API alive and responding? |
| 🔁 `regression` | Deeper checks on data, structure, and behavior |

---

<div align="center">

Made with ☕ and 🧪 · Happy testing! 🎉

</div>
