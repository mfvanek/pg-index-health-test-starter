rootProject.name = "pg-index-health-test-starter-build"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val springBoot = version("spring-boot", "2.7.17")
            plugin("spring-boot-gradlePlugin", "org.springframework.boot")
                .versionRef(springBoot)
            val dependencyManagement = version("spring-dependency-management", "1.1.3")
            plugin("spring-dependency-management", "io.spring.dependency-management")
                .versionRef(dependencyManagement)
            library("spring-boot-starter-root", "org.springframework.boot", "spring-boot-starter")
                .versionRef(springBoot)
            library("spring-boot-starter-test", "org.springframework.boot", "spring-boot-starter-test")
                .versionRef(springBoot)
            library("spring-boot-autoconfigure-processor", "org.springframework.boot", "spring-boot-autoconfigure-processor")
                .versionRef(springBoot)
            library("spring-boot-configuration-processor", "org.springframework.boot", "spring-boot-configuration-processor")
                .versionRef(springBoot)
            library("spring-boot-starter-data-jdbc", "org.springframework.boot", "spring-boot-starter-data-jdbc")
                .versionRef(springBoot)
            library("pitest-dashboard-reporter", "it.mulders.stryker:pit-dashboard-reporter:0.2.1")
            version("pitest-junit5Plugin", "1.2.1")
            version("pitest-core", "1.15.3")
            library("database-h2", "com.h2database:h2:2.2.224")
            library("database-postgresql", "org.postgresql:postgresql:42.6.0")
            library("assertj-core", "org.assertj:assertj-core:3.24.2")
            val pgIndexHealth = version("pg-index-health", "0.10.0")
            library("pg-index-health-core", "io.github.mfvanek", "pg-index-health")
                .versionRef(pgIndexHealth)
            library("pg-index-health-testing", "io.github.mfvanek", "pg-index-health-testing")
                .versionRef(pgIndexHealth)
            library("slf4j-simple", "org.slf4j:slf4j-simple:1.7.36") // to be compatible with Spring Boot 2.7.X
            val commonsLang3 = version("commons-lang3", "3.13.0")
            library("commons-lang3", "org.apache.commons", "commons-lang3")
                .versionRef(commonsLang3)
        }
    }
}

include("pg-index-health-test-starter")
include("h2-demo-app")
include("console-demo-app")
include("postgres-demo-app")
include("pg-index-health-bom")
