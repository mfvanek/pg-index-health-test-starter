plugins {
    id("java-platform")
    id("maven-publish")
    id("signing")
}

description = "pg-index-health library BOM"

dependencies {
    constraints {
        api(libs.pg.index.health.model)
        api(libs.pg.index.health.core)
        api(libs.pg.index.health.jdbcConnection)
        api(libs.pg.index.health.generator)
        api(libs.pg.index.health.testing)
        api(project(":pg-index-health-test-starter"))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenBOM") {
            from(components["javaPlatform"])

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
        sign(publishing.publications["mavenBOM"])
    }
}
