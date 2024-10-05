# Junit playground

A playground for experimenting with Junit and Gradle.

### Test logger plugin

Test logger gives nice looking output for test results.

```shell
% ./gradlew clean test

> Task :app:test

org.example.AppFastTest

  Test AppFastTest_appHasAGreeting1a() PASSED
  Test AppFastTest_appHasAGreeting1b() PASSED
  Test AppFastTest_appHasAGreeting1c() PASSED

org.example.AppSlowTest1

  Test AppSlowTest1_appHasAGreeting1a() PASSED
  Test AppSlowTest1_appHasAGreeting1b() PASSED
  Test AppSlowTest1_appHasAGreeting1c() PASSED

org.example.AppSlowTest2

  Test AppSlowTest2_appHasAGreeting2a() PASSED
  Test AppSlowTest2_appHasAGreeting2b() PASSED
  Test AppSlowTest2_appHasAGreeting2c() PASSED

org.example.JunitClassOrderingTest

  Test testOrdering() PASSED

SUCCESS: Executed 10 tests in 1.1s

BUILD SUCCESSFUL in 1s
```

Note, to get Intellij to recognize the test logger configuration I needed to click the "reload gradle properties" icon
next to the "reload gradle project" icon.

### Ordering tests

Control the order of your junit5 tests - junit.org/junit5/docs/current/user-guide

You can implement your own custom ClassOrderer or use one of the built in implementations. 

See [CustomClassOrderer](./app/src/test/kotlin/org/example/JunitClassOrdering.kt)

When including the custom ClassOrderer (ordering slow tests before fast) in the test configuration, we see the output:

- [junit-platform.properties](./app/src/test/resources/junit-platform.properties)
```properties
junit.jupiter.testclass.order.default = org.example.JunitClassOrdering
```

```shell
% ./gradlew clean test

> Task :app:test

org.example.AppSlowTest1

  Test AppSlowTest1_appHasAGreeting1a() PASSED
  Test AppSlowTest1_appHasAGreeting1b() PASSED
  Test AppSlowTest1_appHasAGreeting1c() PASSED

org.example.AppSlowTest2

  Test AppSlowTest2_appHasAGreeting2a() PASSED
  Test AppSlowTest2_appHasAGreeting2b() PASSED
  Test AppSlowTest2_appHasAGreeting2c() PASSED

org.example.AppFastTest

  Test AppFastTest_appHasAGreeting1a() PASSED
  Test AppFastTest_appHasAGreeting1b() PASSED
  Test AppFastTest_appHasAGreeting1c() PASSED

org.example.JunitClassOrderingTest

  Test testOrdering() PASSED

SUCCESS: Executed 10 tests in 1.2s

BUILD SUCCESSFUL in 2s
```

### Using JVM Test Suite Plugin to separate tests into different types

Use the gradle JVM Test Suite Plugin to separate your tests into different suite types - use:

* ./gradlew test to run your unit tests
* ./gradlew integrationTest to run your integration tests
* define your own test suite types
* ./gradlew check to run all tests, each type after the other

In this project I've added interation tests and a custom test suite type "sampleTestType".

If I run `./gradlew clean check` all 3 test types are run:

* app/src/test
* app/src/integrationTest
* app/src/sampleTestType

```shell
paulrule@192-168-1-168 junit-examples % ./gradlew clean check

> Task :app:sampleTestType

org.example.SampleTypeTest

  Test appHasAGreeting1a() PASSED
  Test appHasAGreeting1b() PASSED
  Test appHasAGreeting1c() PASSED

SUCCESS: Executed 3 tests in 351ms


> Task :app:test

org.example.AppSlowTest1

  Test AppSlowTest1_appHasAGreeting1a() PASSED
  Test AppSlowTest1_appHasAGreeting1b() PASSED
  Test AppSlowTest1_appHasAGreeting1c() PASSED

org.example.AppSlowTest2

  Test AppSlowTest2_appHasAGreeting2a() PASSED
  Test AppSlowTest2_appHasAGreeting2b() PASSED
  Test AppSlowTest2_appHasAGreeting2c() PASSED

org.example.AppFastTest

  Test AppFastTest_appHasAGreeting1a() PASSED
  Test AppFastTest_appHasAGreeting1b() PASSED
  Test AppFastTest_appHasAGreeting1c() PASSED

org.example.JunitClassOrderingTest

  Test testOrdering() PASSED

SUCCESS: Executed 10 tests in 1.1s


> Task :app:integrationTest

org.example.AppIntegrationTest

  AppIntegrationTest3

    AppIntegrationTest2

      Test appHasAGreeting1a() PASSED
      Test appHasAGreeting1b() PASSED
      Test appHasAGreeting1c() PASSED
      Test appHasAGreeting1c() PASSED
      Test appHasAGreeting1c() PASSED
      Test appHasAGreeting1b() PASSED
      Test appHasAGreeting1a() PASSED

org.example.AppIntegrationTest

  AppIntegrationTest3

      Test appHasAGreeting1a() PASSED
      Test appHasAGreeting1b() PASSED

SUCCESS: Executed 9 tests in 338ms

BUILD SUCCESSFUL in 2s
```

### Running in parallel

You can run tests in parallel using the following configuration:

- [junit-platform.properties](./app/src/test/resources/junit-platform.properties)

```properties
junit.jupiter.execution.parallel.enabled = true
junit.jupiter.execution.parallel.mode.default = concurrent
```

Here I've only put this file in the unit test folder `/app/src/test/resources/`

```shell
paulrule@192-168-1-168 junit-examples % ./gradlew clean check

> Task :app:sampleTestType

org.example.SampleTypeTest

  Test appHasAGreeting1a() PASSED
  Test appHasAGreeting1b() PASSED
  Test appHasAGreeting1c() PASSED

SUCCESS: Executed 3 tests in 334ms


> Task :app:test

org.example.AppFastTest

  AppSlowTest1

    AppSlowTest2

      JunitClassOrderingTest

        Test AppSlowTest1_appHasAGreeting1b() PASSED
        Test testOrdering() PASSED
        Test AppSlowTest1_appHasAGreeting1c() PASSED
        Test AppSlowTest1_appHasAGreeting1a() PASSED
        Test AppFastTest_appHasAGreeting1c() PASSED
        Test AppSlowTest2_appHasAGreeting2c() PASSED
        Test AppFastTest_appHasAGreeting1a() PASSED
        Test AppSlowTest2_appHasAGreeting2a() PASSED

  AppSlowTest1

      JunitClassOrderingTest

        Test AppFastTest_appHasAGreeting1b() PASSED
        Test AppSlowTest2_appHasAGreeting2b() PASSED

SUCCESS: Executed 10 tests in 1.1s


> Task :app:integrationTest

org.example.AppIntegrationTest2

  AppIntegrationTest

    AppIntegrationTest3

      Test appHasAGreeting1a() PASSED
      Test appHasAGreeting1c() PASSED
      Test appHasAGreeting1a() PASSED
      Test appHasAGreeting1c() PASSED
      Test appHasAGreeting1b() PASSED
      Test appHasAGreeting1a() PASSED
      Test appHasAGreeting1c() PASSED
      Test appHasAGreeting1b() PASSED

  Test appHasAGreeting1b() PASSED

SUCCESS: Executed 9 tests in 331ms

BUILD SUCCESSFUL in 2s
```

Notice the order of the output in `Task :app:test` is mixed up.