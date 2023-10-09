plugins {
    id("java")
    alias(libs.plugins.spring.boot.gradlePlugin)
    alias(libs.plugins.spring.dependency.management)
    id("io.freefair.lombok") version "8.4"
}

dependencies {
    implementation(project(":pg-index-health-test-starter"))
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation(rootProject.libs.spring.boot.starterDataJdbc)
    implementation(rootProject.libs.pg.index.health.testing)
    implementation(platform("org.testcontainers:testcontainers-bom:1.19.1"))
    implementation("org.testcontainers:postgresql")

    runtimeOnly(rootProject.libs.database.postgresql)

    testImplementation(rootProject.libs.spring.boot.starterTest)
    testImplementation(rootProject.libs.assertj.core)
}
