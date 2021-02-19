package dev.herod.cikey.gradle

import dev.herod.kmpp.files.file
import org.junit.Test

class GradleRootKtTest {

    @Test
    fun gradleProjectForPathTest() {
        val project = gradleRootForPath(file("."))
        println(project)
    }
}
