# githubSearchApp

### Steps to generate code coverage report
----

Go to project root directory

To generate coverage report for mock build:

```sh
$ ./gradlew createMockDebugUnitTestCoverageReport
```
To generate coverage report for prod build:

```sh
$ ./gradlew createProdUnitTestCoverageReport
```

To view the report from terminal

```sh
$ open index.html
```
