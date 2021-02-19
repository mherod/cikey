package dev.herod.kmpp.files

import dev.herod.kmpp.exec
import dev.herod.kx.splitOnSpacing
import kotlinx.coroutines.flow.*
import java.io.File

actual fun file(absolutePath: String): FileMp = FileMpJvm(absolutePath = absolutePath)

data class FileMpJvm(override val absolutePath: String) : FileMp {

    private val file: File by lazy {
        // lazy to avoid unnecessary construction
        File(absolutePath)
    }

    override fun getParent(): FileMp = file(absolutePath = file.parentFile.absolutePath)

    override fun isDirectory(): Boolean = file.isDirectory

    override fun isFile(): Boolean = file.isFile

    override fun listFiles(): Flow<FileMp> = flow {
        emitAll(
            flow = file.listFiles()
                .orEmpty()
                .asFlow()
                .map { file(it.absolutePath) }
        )
    }

    override fun size(): Long = file.length()

    override fun readLines(): Flow<String> = flow {
        emitAll(
            flow = file.readLines().asFlow()
        )
    }

    override fun shasum(): Flow<String> = flow {
        emitAll(
            flow = exec("shasum ${file.absolutePath}")
                .flatMapConcat {
                    val split = it.splitOnSpacing()
                    if (split.size == 2 && split.get(1) == absolutePath) {
                        flowOf(split.first())
                    } else {
                        emptyFlow()
                    }
                }
        )
    }
}
