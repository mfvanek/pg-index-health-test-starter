plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("com.github.spotbugs.snom:spotbugs-gradle-plugin:5.2.4")
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:4.4.1.3373")
    implementation("net.ltgt.gradle:gradle-errorprone-plugin:3.1.0")

    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter-api")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
