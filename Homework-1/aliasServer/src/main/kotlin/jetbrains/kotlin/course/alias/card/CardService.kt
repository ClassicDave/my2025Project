package jetbrains.kotlin.course.alias.card
import jetbrains.kotlin.course.alias.util.Identifier
import jetbrains.kotlin.course.alias.util.IdentifierFactory
import org.springframework.stereotype.Service
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

@Serializable
data class UsedWords(val words: List<String>)

class CardService {
    private val identifierFactory = IdentifierFactory()
    private val words = listOf("apple", "banana", "cherry", "date", "elephant", "frog", "grape", "house")
    
    companion object {
        const val WORDS_IN_CARD = 4
        val cardsAmount = words.size / WORDS_IN_CARD
    }

    val cards: List<Card> = generateCards()
    private val usedWords = loadUsedWords().toMutableSet()

    private fun List<String>.toWords() = map { Word(it) }

    private fun generateCards(): List<Card> {
        return words.filter { it !in usedWords }.shuffled()
            .chunked(WORDS_IN_CARD)
            .take(cardsAmount)
            .map { Card(identifierFactory.uniqueIdentifier(), it.toWords()) }
    }

    fun markWordAsUsed(word: String) {
        usedWords.add(word)
        saveUsedWords()
    }

    private fun saveUsedWords() {
        val json = Json.encodeToString(UsedWords(usedWords.toList()))
        File("usedWords.json").writeText(json)
    }

    private fun loadUsedWords(): List<String> {
        val file = File("usedWords.json")
        return if (file.exists()) {
            Json.decodeFromString<UsedWords>(file.readText()).words
        } else emptyList()
    }
}

