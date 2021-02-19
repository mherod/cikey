package dev.herod.kmpp.files

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FileMpKtTest {

    @Test
    fun walkTest() {
        runBlocking {
            file("/Users/matthewherod/Development")
                .walkTopDown(filesOnly = false)
                .collect { fileMp ->
                    println(fileMp.absolutePath)
                }
        }
    }
}
