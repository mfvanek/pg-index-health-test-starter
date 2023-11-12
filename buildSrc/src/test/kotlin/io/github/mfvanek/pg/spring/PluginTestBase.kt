package io.github.mfvanek.pg.spring

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

internal abstract class PluginTestBase {

    @TempDir
    protected lateinit var testProjectDir: Path
    private lateinit var settingsFile: File
    protected lateinit var buildFile: File

    @BeforeEach
    protected fun setupBuildFile() {
        settingsFile = Files.createFile(testProjectDir.resolve("settings.gradle.kts")).toFile()
        settingsFile.appendText("rootProject.name = \"test\"")
        buildFile = Files.createFile(testProjectDir.resolve("build.gradle.kts")).toFile()
    }

    protected fun runTaskWithFailure(task: String): BuildResult {
        return GradleRunner.create()
            .withProjectDir(testProjectDir.toFile())
            .withArguments(task, "--stacktrace")
            .withPluginClasspath()
            .withDebug(true)
            .forwardOutput()
            .buildAndFail()
    }
}
