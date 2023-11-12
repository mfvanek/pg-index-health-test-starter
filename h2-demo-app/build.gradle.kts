plugins {
    id("pg-index-health-test-starter.java-conventions")
    alias(libs.plugins.spring.boot.gradlePlugin)
    alias(libs.plugins.spring.dependency.management)
}

ext["commons-lang3.version"] = libs.versions.commons.lang3.get()

dependencies {
    implementation(project(":pg-index-health-test-starter"))
    implementation(libs.spring.boot.starter.data.jdbc)

    runtimeOnly(libs.database.h2)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.assertj.core)

    spotbugsSlf4j(libs.slf4j.simple)
}
