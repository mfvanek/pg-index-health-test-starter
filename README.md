# pg-index-health-test-starter
Spring Boot Starter for using [pg-index-health](https://github.com/mfvanek/pg-index-health) library in tests.

[![Java CI](https://github.com/mfvanek/pg-index-health-test-starter/workflows/Java%20CI/badge.svg)](https://github.com/mfvanek/pg-index-health-test-starter/actions "Java CI")
[![Maven Central](https://img.shields.io/maven-central/v/io.github.mfvanek/pg-index-health-test-starter.svg)](https://search.maven.org/artifact/io.github.mfvanek/pg-index-health-test-starter/ "Maven Central")
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/mfvanek/pg-index-health-test-starter/blob/master/LICENSE "Apache License 2.0")
[![codecov](https://codecov.io/gh/mfvanek/pg-index-health-test-starter/branch/master/graph/badge.svg?token=1C3SANSWIT)](https://codecov.io/gh/mfvanek/pg-index-health-test-starter)
[![javadoc](https://javadoc.io/badge2/io.github.mfvanek/pg-index-health-test-starter/javadoc.svg)](https://javadoc.io/doc/io.github.mfvanek/pg-index-health-test-starter "javadoc")

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=bugs)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=coverage)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter)

[![Mutation score](https://img.shields.io/badge/Mutation%20score-100%25-green)](https://pitest.org/ "Mutation score 100%")

## Installation
Using Gradle:
```groovy
testImplementation 'io.github.mfvanek:pg-index-health-test-starter:0.6.1'
```

Using Maven:
```xml
<dependency>
  <groupId>io.github.mfvanek</groupId>
  <artifactId>pg-index-health-test-starter</artifactId>
  <version>0.6.1</version>
  <scope>test</scope>
</dependency>
```

## Compatibility
### Java versions
Requires [Java 8](https://www.java.com/en/)

### Spring Boot versions

| Spring Boot | pg-index-health-test-starter |
|-------------|------------------------------|
| 2.4.x       | 0.3.x — 0.4.x                |
| 2.5.x       | 0.5.x — 0.6.x                |
