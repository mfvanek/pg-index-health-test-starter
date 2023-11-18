package io.github.mfvanek.pg.spring

import org.gradle.internal.impldep.org.junit.Assert.assertTrue
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Files

internal class JavaConventionPluginTest : PluginTestBase() {

    @BeforeEach
    fun init() {
        buildFile.appendText("""
            |plugins {
            |    id("pg-index-health-test-starter.java-conventions")
            |}
            |
            |repositories {
            |    mavenLocal()
            |    mavenCentral()
            |}
            |
            |tasks.withType<com.github.spotbugs.snom.SpotBugsTask>().configureEach {
            |    onlyIf { false }
            |}
        """.trimMargin())
    }

    @Test
    fun `fails while configuring checkstyle`() {
        Files.createDirectories(testProjectDir.resolve("src/main/java/com/example/"))
        Files.createFile(testProjectDir.resolve("src/main/java/com/example/Foo.java"))
            .toFile()
            .appendText("""
            |package com.example;
            |
            |import java.util.*;
            |
            |class Foo {
            |    void bar() {
            |    }
            |}
        """.trimMargin())

        val result = runTaskWithFailure("build")

        assertEquals(TaskOutcome.FAILED, result.task(":checkstyleMain")?.outcome)
        assertTrue(result.output.contains("Execution failed for task ':checkstyleMain'."))
        assertTrue(result.output.contains("An unexpected error occurred configuring and executing Checkstyle."))
    }
}
