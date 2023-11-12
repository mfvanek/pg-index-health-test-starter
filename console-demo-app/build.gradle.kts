plugins {
    id("pg-index-health-test-starter.java-conventions")
    alias(libs.plugins.spring.boot.gradlePlugin)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    implementation(project(":pg-index-health-test-starter"))
    implementation(rootProject.libs.spring.boot.starter.root)

    testImplementation(rootProject.libs.spring.boot.starter.test)
    testImplementation(rootProject.libs.assertj.core)

    spotbugsSlf4j(rootProject.libs.slf4j.simple)
    spotbugsSlf4j("org.apache.commons:commons-lang3:3.13.0") // TODO
}
