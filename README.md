# SavageDiceBot

![Master Branch](https://github.com/mdelapenya/savagedicebot-telegram/workflows/Pushes%20to%20master/badge.svg?branch=master)
[![codecov](https://codecov.io/gh/mdelapenya/savagedicebot-telegram/branch/master/graph/badge.svg)](https://codecov.io/gh/mdelapenya/savagedicebot-telegram)

This Telegram bot allows rolling one or more savage worlds RPG dice.

## Building the project
The project uses a Gradle wrapper, so it's required to install the following build dependencies:

- Java JDK 8 or above
- An Java-based IDE, such as IntelliJ

To connect to Telegram APIs you will need a valid token, please create a `.env` file at the root directory of your project (is excluded by Git), it will be used to communicate with Telegram. Please see [sample.env](./sample.env).

### Compiling the project
- Linux or Mac:
```shell
$ ./gradlew compileJava
```
- Windows:
```shell
$ ./gradlew.bat compileJava
```

### Packaging:
- Linux or Mac:
```shell
$ ./gradlew jar
```
- Windows:
```shell
$ ./gradlew.bat jar
```

The result will be a .JAR file located in the `build/libs` directory (ignored by Git)

## Test execution
Unit tests are located under the `test` directory:

- Linux or Mac:
```shell
$ ./gradlew test
```
- Windows:
```shell
$ ./gradlew.bat test
```
