import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import com.github.spotbugs.snom.SpotBugsTask
import net.ltgt.gradle.errorprone.errorprone
import org.sonarqube.gradle.SonarTask

plugins {
    id("java")
    id("jacoco")
    id("com.github.spotbugs") version "5.1.1"
    id("checkstyle")
    id("pmd")
    id("org.sonarqube") version "4.3.0.3225"
    id("net.ltgt.errorprone") version "3.1.0"
    id("com.github.ben-manes.versions") version "0.47.0"
}

description = "pg-index-health-test-starter build"

allprojects {
    group = "io.github.mfvanek"
    version = "0.9.4"

    repositories {
        mavenLocal()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.sonarqube")
    apply(plugin = "checkstyle")
    apply(plugin = "pmd")
    apply(plugin = "com.github.spotbugs")
    apply(plugin = "jacoco")
    apply(plugin = "net.ltgt.errorprone")

    dependencies {
        testImplementation(platform("org.junit:junit-bom:5.10.0"))

        checkstyle("com.thomasjensen.checkstyle.addons:checkstyle-addons:7.0.1")
        errorprone("com.google.errorprone:error_prone_core:2.21.0")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        withJavadocJar()
        withSourcesJar()
    }
    tasks.withType<JavaCompile>().configureEach {
        options.errorprone {
            disableWarningsInGeneratedCode.set(true)
        }
    }

    tasks {
        test {
            useJUnitPlatform()
            dependsOn(checkstyleMain, checkstyleTest, pmdMain, pmdTest, spotbugsMain, spotbugsTest)
            finalizedBy(jacocoTestReport, jacocoTestCoverageVerification)
        }

        jar {
            manifest {
                attributes["Implementation-Title"] = project.name
                attributes["Implementation-Version"] = project.version
            }
            isPreserveFileTimestamps = false
            isReproducibleFileOrder = true
        }

        javadoc {
            if (JavaVersion.current().isJava9Compatible) {
                (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
            }
        }

        jacocoTestReport {
            dependsOn(test)
            reports {
                xml.required.set(true)
                html.required.set(true)
            }
        }

        jacocoTestCoverageVerification {
            dependsOn(jacocoTestReport)
            violationRules {
                rule {
                    limit {
                        counter = "CLASS"
                        value = "MISSEDCOUNT"
                        maximum = "0.0".toBigDecimal()
                    }
                }
                rule {
                    limit {
                        counter = "METHOD"
                        value = "MISSEDCOUNT"
                        maximum = "0.0".toBigDecimal()
                    }
                }
                rule {
                    limit {
                        counter = "LINE"
                        value = "MISSEDCOUNT"
                        maximum = "0.0".toBigDecimal()
                    }
                }
                rule {
                    limit {
                        counter = "INSTRUCTION"
                        value = "COVEREDRATIO"
                        minimum = "1.0".toBigDecimal()
                    }
                }
            }
        }

        check {
            dependsOn(jacocoTestCoverageVerification)
        }

        withType<SonarTask>().configureEach {
            dependsOn(test, jacocoTestReport)
        }
    }

    jacoco {
        toolVersion = rootProject.libs.versions.jacoco.get()
    }

    checkstyle {
        toolVersion = rootProject.libs.versions.checkstyle.get()
        configFile = file("../config/checkstyle/checkstyle.xml")
        isIgnoreFailures = false
        maxWarnings = 0
        maxErrors = 0
    }

    pmd {
        toolVersion = rootProject.libs.versions.pmd.get()
        isConsoleOutput = true
        ruleSetFiles = files("../config/pmd/pmd.xml")
        ruleSets = listOf()
    }

    spotbugs {
        showProgress.set(true)
        effort.set(Effort.MAX)
        reportLevel.set(Confidence.LOW)
        excludeFilter.set(file("../config/spotbugs/exclude.xml"))
    }
    tasks.withType<SpotBugsTask>().configureEach {
        reports {
            create("xml") { enabled = true }
            create("html") { enabled = true }
        }
    }
}

sonar {
    properties {
        property("sonar.projectKey", "mfvanek_pg-index-health-test-starter")
        property("sonar.organization", "mfvanek")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.exclusions", "**/build.gradle.kts")
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    checkForGradleUpdate = true
    gradleReleaseChannel = "current"
    checkConstraints = true
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

// To avoid creation of jar's in build folder in the root
tasks {
    jar {
        isEnabled = false
    }
}
