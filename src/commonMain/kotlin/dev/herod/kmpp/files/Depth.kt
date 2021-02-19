package dev.herod.kmpp.files

fun FileMp.depth() = absolutePath.count { it == '/' }
