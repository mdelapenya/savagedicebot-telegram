# SavageDiceBot
Este bot sirve para devolver el resultado de la tirada de uno o varios dados, y enviarlo a Telegram en un bot.

## Construcción del proyecto
El proyecto usa gradle, en particular su wrapper, por lo que para construirlo será necesario tener disponibles las siguientes dependencias:

- Java JDK 8 o superior
- Netbeans o IntelliJ

### Para compilar el proyecto:
- En linux o Mac:
```shell
$ ./gradlew compileJava
```
- Windows:
```shell
$ ./gradlew.bat compileJava
```

### Para empaquetar proyecto:
- En linux o Mac:
```shell
$ ./gradlew jar
```
- Windows:
```shell
$ ./gradlew.bat jar
```
El resultado será un archivo .JAR en el directorio `build/libs` del proyecto. (este dirctorio está ignorado en git)

## Ejecución de los tests
En el directorio `test` están los test unitarios, que se ejecutan sin invocar la red.

- En linux o Mac:
```shell
$ ./gradlew test
```
- Windows:
```shell
$ ./gradlew.bat test
```
