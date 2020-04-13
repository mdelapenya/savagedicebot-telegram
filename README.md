# SavageDiceBot
This Telegram bot allows rolling one or more savage worlds RPG dice.

## Building the project
The project uses a Gradle wrapper, so it's required to install the following build dependencies:

- Java JDK 8 or above
- An Java-based IDE, such as IntelliJ

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
