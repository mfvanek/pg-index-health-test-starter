plugins {
    id("pg-index-health-test-starter.java-compilation")
    id("pg-index-health-test-starter.java-conventions")
    alias(libs.plugins.spring.boot.gradlePlugin)
    alias(libs.plugins.spring.dependency.management)
    id("io.freefair.lombok") version "8.6"
}

ext["commons-lang3.version"] = libs.versions.commons.lang3.get()

dependencies {
    implementation(project(":pg-index-health-test-starter"))
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation(libs.spring.boot.starter.data.jdbc)
    implementation(libs.pg.index.health.testing)
    implementation(platform(libs.testcontainers.bom))
    implementation("org.testcontainers:postgresql")

    runtimeOnly(libs.database.postgresql)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.assertj.core)

    spotbugsSlf4j(libs.slf4j.simple)
}

lombok {
    version = "1.18.32"
}
