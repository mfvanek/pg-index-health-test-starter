plugins {
    id("java")
    alias(libs.plugins.spring.boot.gradlePlugin)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    implementation(project(":pg-index-health-test-starter"))
    implementation(rootProject.libs.spring.boot.starter.data.jdbc)

    runtimeOnly(rootProject.libs.database.h2)

    testImplementation(rootProject.libs.spring.boot.starter.test)
    testImplementation(rootProject.libs.assertj.core)

    spotbugsSlf4j(rootProject.libs.slf4j.simple)
}
