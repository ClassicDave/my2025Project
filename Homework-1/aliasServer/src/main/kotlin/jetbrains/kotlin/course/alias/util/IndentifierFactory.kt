package jetbrains.kotlin.course.alias.util

import java.io.File

typealias Identifier = Int


class IdentifierFactory {
    private var counter: Identifier = loadCounter()

    fun uniqueIdentifier(): Identifier {
        counter++
        saveCounter()
        return counter
    }

    private fun saveCounter() {
        File("identifierFactory.txt").writeText(counter.toString())
    }

    private fun loadCounter(): Identifier {
        return File("identifierFactory.txt").takeIf { it.exists() }?.readText()?.toIntOrNull() ?: 0
    }
}
