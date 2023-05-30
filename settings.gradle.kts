rootProject.name = "pg-index-health-test-starter"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val springBoot = version("spring-boot", "2.7.12")
            library("spring-boot-starter", "org.springframework.boot", "spring-boot-starter")
                .versionRef(springBoot)
            library("spring-boot-starterTest", "org.springframework.boot", "spring-boot-starter-test")
                .versionRef(springBoot)
            library("spring-boot-autoconfigureProcessor", "org.springframework.boot", "spring-boot-autoconfigure-processor")
                .versionRef(springBoot)
            library("spring-boot-configurationProcessor", "org.springframework.boot", "spring-boot-configuration-processor")
                .versionRef(springBoot)
        }
    }
}
