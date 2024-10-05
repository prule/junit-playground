# Junit playground

A playground for experimenting with Junit and Gradle.

### Test logger plugin

Test logger gives nice looking output for test results.

```shell
% ./gradlew clean test

> Task :app:test

org.example.AppTest2

  AppTest1

    AppFastTest

      Test appHasAGreeting1c() PASSED
      Test appHasAGreeting1a() PASSED
      Test appHasAGreeting2c() PASSED
      Test appHasAGreeting1c() PASSED
      Test appHasAGreeting2b() PASSED
      Test appHasAGreeting1a() PASSED
      Test appHasAGreeting2a() PASSED

org.example.AppTest2

      Test appHasAGreeting1b() PASSED
      Test appHasAGreeting1b() PASSED

SUCCESS: Executed 9 tests in 380ms

BUILD SUCCESSFUL in 1s
```

Note, to get Intellij to recognize the test logger configuration I needed to click the "reload gradle properties" icon
next to the "reload gradle project" icon. 