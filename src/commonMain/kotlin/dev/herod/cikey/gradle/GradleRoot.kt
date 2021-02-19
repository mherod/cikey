package dev.herod.cikey.gradle

import dev.herod.kmpp.files.FileMp
import dev.herod.kmpp.files.walkTopDown
import dev.herod.kmpp.runBlocking
import dev.herod.kx.flow.shortestBy
import kotlinx.coroutines.flow.filter

fun gradleRootForPath(rootPath: FileMp) = runBlocking {
    GradleRoot(

        rootPath = rootPath,

        settingsFile = rootPath.walkTopDown(
            maxDepth = 1,
            filesOnly = true
        ).filter {
            "settings.gradle" in it.absolutePath
        }.shortestBy { it.absolutePath },

        buildFile = rootPath.walkTopDown(
            maxDepth = 1,
            filesOnly = true
        ).filter {
            "build.gradle" in it.absolutePath
        }.shortestBy { it.absolutePath },
    )
}

data class GradleRoot(
    val rootPath: FileMp,
    val settingsFile: FileMp,
    val buildFile: FileMp,
) {
    init {
        require(rootPath.isDirectory())
        require(settingsFile.isFile())
        require(buildFile.isFile())
    }
}
