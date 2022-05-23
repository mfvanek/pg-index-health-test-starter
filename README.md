# pg-index-health-test-starter
Spring Boot Starter for using [pg-index-health](https://github.com/mfvanek/pg-index-health) library in tests.

[![Java CI](https://github.com/mfvanek/pg-index-health-test-starter/workflows/Java%20CI/badge.svg)](https://github.com/mfvanek/pg-index-health-test-starter/actions "Java CI")
[![Maven Central](https://img.shields.io/maven-central/v/io.github.mfvanek/pg-index-health-test-starter.svg)](https://search.maven.org/artifact/io.github.mfvanek/pg-index-health-test-starter/ "Maven Central")
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/mfvanek/pg-index-health-test-starter/blob/master/LICENSE "Apache License 2.0")
[![codecov](https://codecov.io/gh/mfvanek/pg-index-health-test-starter/branch/master/graph/badge.svg?token=1C3SANSWIT)](https://codecov.io/gh/mfvanek/pg-index-health-test-starter)
[![javadoc](https://javadoc.io/badge2/io.github.mfvanek/pg-index-health-test-starter/javadoc.svg)](https://javadoc.io/doc/io.github.mfvanek/pg-index-health-test-starter "javadoc")

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter "security")
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter "maintainability")
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter "vulnerabilities")
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter "code smells")
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter "lines of code")

## Installation
Using Gradle:
```groovy
testImplementation 'io.github.mfvanek:pg-index-health-test-starter:0.4.0'
```

Using Maven:
```xml
<dependency>
  <groupId>io.github.mfvanek</groupId>
  <artifactId>pg-index-health-test-starter</artifactId>
  <version>0.4.0</version>
  <scope>test</scope>
</dependency>
```

## Compatibility
### Java versions
Requires [Java 8](https://www.java.com/en/)

### Spring Boot versions

| Spring Boot | pg-index-health-test-starter |
|-------------|------------------------------|
| 2.4.x       | 0.3.x                        |
| 2.4.x       | 0.4.0                        |
