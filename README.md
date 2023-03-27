# quarkustemplate

## Description

This is a minimal template for a Quarkus application with Scala 2.13 and Gradle. It includes the following features:

- Scala 2.13
- Anorm
- Scalatags
- Flyway
- Micrometer

The majority of use cases should be covered by this setup. Anorm was chosen instead of Quarkus persistence solutions
because this way you get to use all Scala, without adapting to the Java-centric expectations of Hibernate and other
components. Scalatags is used for HTML templating, because it is a very simple and lightweight library that statically
compiles to bytecode.

Any other Quarkus extension should just work, provided you are careful in providing the bridge for the Scala-Java 
divide.

The rest of the readme is the default Quarkus readme.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/quarkustemplate-1.0.0-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

## Related Guides

- Flyway ([guide](https://quarkus.io/guides/flyway)): Handle your database schema migrations
- Micrometer Registry Prometheus ([guide](https://quarkus.io/guides/micrometer)): Enable Prometheus support for Micrometer
