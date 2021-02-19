package dev.herod.kmpp.files

import kotlinx.coroutines.flow.*
import java.io.File

actual fun FileMp.walkTopDown(maxDepth: Int, filesOnly: Boolean): Flow<FileMp> = flow {
    emitAll(
        flow = File(absolutePath)
            .walkTopDown()
            .run { if (maxDepth > 0) maxDepth(maxDepth) else this }
            .iterator()
            .asFlow()
            .map { file(it.absolutePath) }
    )
}
