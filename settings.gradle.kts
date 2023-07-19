rootProject.name = "pg-index-health-test-starter"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val springBoot = version("spring-boot", "2.7.13")
            library("spring-boot-starter", "org.springframework.boot", "spring-boot-starter")
                .versionRef(springBoot)
            library("spring-boot-starterTest", "org.springframework.boot", "spring-boot-starter-test")
                .versionRef(springBoot)
            library("spring-boot-autoconfigureProcessor", "org.springframework.boot", "spring-boot-autoconfigure-processor")
                .versionRef(springBoot)
            library("spring-boot-configurationProcessor", "org.springframework.boot", "spring-boot-configuration-processor")
                .versionRef(springBoot)
            version("checkstyle", "10.12.0")
            version("pmd", "6.55.0")
            version("jacoco", "0.8.10")
            library("pitest-dashboard-reporter", "it.mulders.stryker:pit-dashboard-reporter:0.2.1")
            version("pitest-junit5Plugin", "1.2.0")
            version("pitest-core", "1.14.1")
        }
    }
}
