package dev.herod.kx.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.reduce

suspend fun Flow<String>.shortest(): String = reduce { accumulator, value ->
    when {
        value.length < accumulator.length -> value
        else -> accumulator
    }.trim()
}

suspend inline fun <reified T> Flow<T>.shortestBy(crossinline function: (T) -> String): T = reduce { acc, value ->
    val a: String = function(acc)
    val b: String = function(value)
    when {
        b.length < a.length -> value
        else -> acc
    }
}
