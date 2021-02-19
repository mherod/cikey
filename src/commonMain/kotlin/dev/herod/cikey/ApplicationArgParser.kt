package dev.herod.cikey

import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli

@OptIn(ExperimentalCli::class)
class ApplicationArgParser : ArgParser(programName = "cikey") {
    init {
        subcommands(
        )
    }
}
