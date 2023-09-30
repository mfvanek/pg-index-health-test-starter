rootProject.name = "pg-index-health-test-starter-build"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val springBoot = version("spring-boot", "2.7.14")
            plugin("spring-boot-gradlePlugin", "org.springframework.boot")
                .versionRef(springBoot)
            val dependencyManagement = version("spring-dependency-management", "1.1.2")
            plugin("spring-dependency-management", "io.spring.dependency-management")
                .versionRef(dependencyManagement)
            library("spring-boot-starter", "org.springframework.boot", "spring-boot-starter")
                .versionRef(springBoot)
            library("spring-boot-starterTest", "org.springframework.boot", "spring-boot-starter-test")
                .versionRef(springBoot)
            library("spring-boot-autoconfigureProcessor", "org.springframework.boot", "spring-boot-autoconfigure-processor")
                .versionRef(springBoot)
            library("spring-boot-configurationProcessor", "org.springframework.boot", "spring-boot-configuration-processor")
                .versionRef(springBoot)
            library("spring-boot-starterDataJdbc", "org.springframework.boot", "spring-boot-starter-data-jdbc")
                .versionRef(springBoot)
            version("checkstyle", "10.12.0")
            version("pmd", "6.55.0")
            version("jacoco", "0.8.10")
            library("pitest-dashboard-reporter", "it.mulders.stryker:pit-dashboard-reporter:0.2.1")
            version("pitest-junit5Plugin", "1.2.0")
            version("pitest-core", "1.14.2")
            library("database-h2", "com.h2database:h2:2.2.224")
            library("database-postgresql", "org.postgresql:postgresql:42.6.0")
            library("assertj-core", "org.assertj:assertj-core:3.24.2")
            val pgIndexHealth = version("pg-index-health", "0.9.5")
            library("pg-index-health-core", "io.github.mfvanek", "pg-index-health")
                .versionRef(pgIndexHealth)
            library("pg-index-health-testing", "io.github.mfvanek", "pg-index-health-testing")
                .versionRef(pgIndexHealth)
        }
    }
}

include("pg-index-health-test-starter")
include("h2-demo-app")
include("console-demo-app")
include("postgres-demo-app")
