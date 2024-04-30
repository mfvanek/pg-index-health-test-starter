import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    id("pg-index-health-test-starter.java-compilation")
    alias(libs.plugins.spring.boot.gradlePlugin)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.detekt)
}

ext["commons-lang3.version"] = libs.versions.commons.lang3.get()

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation(project(":pg-index-health-test-starter"))
    implementation(libs.spring.boot.starter.data.jdbc)
    implementation(libs.pg.index.health.testing)
    implementation(platform(libs.testcontainers.bom))
    implementation("org.testcontainers:postgresql")

    runtimeOnly(libs.database.postgresql)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.assertj.core)

    detektPlugins(libs.detekt.formatting)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "11"
    }
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom(file("../config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}

tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
