import info.solidsoft.gradle.pitest.PitestTask

description = "Spring Boot Starter for pg-index-health library"

plugins {
    id("pg-index-health-test-starter.java-conventions")
    id("java-library")
    id("maven-publish")
    id("signing")
    id("info.solidsoft.pitest") version "1.15.0"
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api(libs.pg.index.health.core)
    implementation(libs.spring.boot.starter.root)
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    annotationProcessor(libs.spring.boot.autoconfigure.processor)
    annotationProcessor(libs.spring.boot.configuration.processor)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation(libs.commons.lang3)
    testImplementation(libs.assertj.core)
    testImplementation(libs.database.postgresql)

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    pitest(libs.pitest.dashboard.reporter)
}

pitest {
    verbosity.set("DEFAULT")
    junit5PluginVersion.set(libs.versions.pitest.junit5Plugin.get())
    pitestVersion.set(libs.versions.pitest.core.get())
    threads.set(4)
    if (System.getenv("STRYKER_DASHBOARD_API_KEY") != null) {
        outputFormats.set(setOf("stryker-dashboard"))
    } else {
        outputFormats.set(setOf("HTML"))
    }
    timestampedReports.set(false)
    mutationThreshold.set(100)
}
tasks.withType<PitestTask>().configureEach {
    mustRunAfter(tasks.test)
}
tasks.build {
    dependsOn("pitest")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }

            pom {
                name.set(project.name)
                description.set(project.provider(project::getDescription))
                url.set("https://github.com/mfvanek/pg-index-health-test-starter")
                licenses {
                    license {
                        name.set("Apache License Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }

                developers {
                    developer {
                        id.set("mfvanek")
                        name.set("Ivan Vakhrushev")
                        email.set("mfvanek@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/mfvanek/pg-index-health-test-starter.git")
                    developerConnection.set("scm:git@github.com:mfvanek/pg-index-health-test-starter.git")
                    url.set("https://github.com/mfvanek/pg-index-health-test-starter")
                }
            }
        }
    }

    repositories {
        maven {
            val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

            val sonatypeUsername: String by project
            val sonatypePassword: String by project
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
        }
    }
}

signing {
    if (!version.toString().endsWith("SNAPSHOT")) {
        useGpgCmd()
        sign(publishing.publications["mavenJava"])
    }
}
