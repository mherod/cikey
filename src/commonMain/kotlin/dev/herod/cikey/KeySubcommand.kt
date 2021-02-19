package dev.herod.cikey

import dev.herod.cikey.gradle.gradleRootForPath
import dev.herod.kmpp.files.file
import dev.herod.kmpp.files.walkTopDown
import dev.herod.kmpp.runBlocking
import dev.herod.kx.flow.shortestBy
import kotlinx.cli.ArgType
import kotlinx.cli.Subcommand
import kotlinx.coroutines.flow.*

class KeySubcommand : Subcommand("key", "key") {

    private val path: String by argument(ArgType.String)

    override fun execute() {
        runBlocking {

            val project = gradleRootForPath(rootPath = file(path))
            println(project)

            val regex = ".+/src/.+".toRegex()
            project.rootPath
                .walkTopDown(filesOnly = true)
                .filter { regex.matches(it.absolutePath) }
                .mapNotNull { file ->
                    "${file.shasum().single()} // ${file.absolutePath}"
                }
                .collect { s ->
                    println(s)
                }
        }
    }
}
