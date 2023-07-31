plugins {
    id("java")
    alias(libs.plugins.spring.boot.gradlePlugin)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    implementation(project(":pg-index-health-test-starter"))
    implementation(rootProject.libs.spring.boot.starter)

    testImplementation(rootProject.libs.spring.boot.starterTest)
    testImplementation(rootProject.libs.assertj.core)
}
