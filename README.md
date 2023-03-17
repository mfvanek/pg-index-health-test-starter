# pg-index-health-test-starter
Spring Boot Starter for using [pg-index-health](https://github.com/mfvanek/pg-index-health) library in tests.

[![Java CI](https://github.com/mfvanek/pg-index-health-test-starter/workflows/Java%20CI/badge.svg)](https://github.com/mfvanek/pg-index-health-test-starter/actions "Java CI")
[![Maven Central](https://img.shields.io/maven-central/v/io.github.mfvanek/pg-index-health-test-starter.svg)](https://search.maven.org/artifact/io.github.mfvanek/pg-index-health-test-starter/ "Maven Central")
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/mfvanek/pg-index-health-test-starter/blob/master/LICENSE "Apache License 2.0")
[![codecov](https://codecov.io/gh/mfvanek/pg-index-health-test-starter/branch/master/graph/badge.svg?token=1C3SANSWIT)](https://codecov.io/gh/mfvanek/pg-index-health-test-starter)
[![javadoc](https://javadoc.io/badge2/io.github.mfvanek/pg-index-health-test-starter/javadoc.svg)](https://javadoc.io/doc/io.github.mfvanek/pg-index-health-test-starter "javadoc")

[![Total lines](https://tokei.rs/b1/github/mfvanek/pg-index-health-test-starter)](https://github.com/mfvanek/pg-index-health-test-starter)
[![Files](https://tokei.rs/b1/github/mfvanek/pg-index-health-test-starter?category=files)](https://github.com/mfvanek/pg-index-health-test-starter)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=bugs)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mfvanek_pg-index-health-test-starter&metric=coverage)](https://sonarcloud.io/summary/new_code?id=mfvanek_pg-index-health-test-starter)

[![Mutation testing badge](https://img.shields.io/endpoint?style=flat&url=https%3A%2F%2Fbadge-api.stryker-mutator.io%2Fgithub.com%2Fmfvanek%2Fpg-index-health-test-starter%2Fmaster)](https://dashboard.stryker-mutator.io/reports/github.com/mfvanek/pg-index-health-test-starter/master)

## Installation
Using Gradle:
```groovy
testImplementation 'io.github.mfvanek:pg-index-health-test-starter:0.9.0'
```

Using Maven:
```xml
<dependency>
  <groupId>io.github.mfvanek</groupId>
  <artifactId>pg-index-health-test-starter</artifactId>
  <version>0.9.0</version>
  <scope>test</scope>
</dependency>
```

## Compatibility
### Java versions
Requires [Java 11](https://www.java.com/en/)  
For **Java 8** compatible version take a look at release [0.7.0](https://github.com/mfvanek/pg-index-health-test-starter/releases/tag/v.0.7.0) and lower

### Spring Boot versions

| Spring Boot | Min JDK | pg-index-health-test-starter |
|-------------|---------|------------------------------|
| 2.4.x       | 8       | 0.3.x — 0.4.x                |
| 2.5.x       | 8       | 0.5.x — 0.6.x                |
| 2.6.x       | 8       | 0.7.x                        |
| 2.7.x       | 11      | 0.8.x — 0.9.x                |
