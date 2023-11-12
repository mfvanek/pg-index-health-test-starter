plugins {
    id("java-platform")
}

description = "pg-index-health library BOM"

dependencies {
    constraints {
        api(project(":pg-index-health-test-starter"))
    }
}

// TODO publishing
