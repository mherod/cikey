package dev.herod.kmpp.files

import dev.herod.kmpp.exec
import kotlinx.coroutines.flow.*

actual fun FileMp.walkTopDown(maxDepth: Int, filesOnly: Boolean): Flow<FileMp> = flow {
    emitAll(
        flow = exec(
            command = buildString {
                append("find $absolutePath")
                if (maxDepth > 0) {
                    append(" -maxdepth $maxDepth")
                }
                if (filesOnly) {
                    append(" -type f")
                }
            }
        ).filter {
            ':' !in it && it.startsWith(absolutePath)
        }.map { file(it) }
    )
}
