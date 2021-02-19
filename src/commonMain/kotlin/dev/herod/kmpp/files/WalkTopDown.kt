package dev.herod.kmpp.files

import kotlinx.coroutines.flow.*

expect fun FileMp.walkTopDown(maxDepth: Int = -1, filesOnly: Boolean): Flow<FileMp>

@Suppress("unused")
fun FileMp.crudeWalkTopDown(): Flow<FileMp> {
    val rootFile = this
    return when {
        isDirectory() -> {
            flow {
                emit(rootFile)
                if (rootFile.isDirectory()) {
                    emitAll(flow = rootFile.listFiles())
                }
            }.filterNot { fileMp ->
                fileMp == rootFile
            }.flatMapConcat { fileMp ->
                fileMp.crudeWalkTopDown()
            }
        }
        else -> {
            flowOf(rootFile)
        }
    }
}
