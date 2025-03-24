package jetbrains.kotlin.course.alias.results
import jetbrains.kotlin.course.alias.team.Team
import jetbrains.kotlin.course.alias.team.TeamService
import org.springframework.stereotype.Service

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

@Serializable
data class SerializableGameResult(val teams: List<SerializableTeam>)

typealias GameResult = List<Team>

@Service
class GameResultsService {
    companion object {
        val gameHistory: MutableList<GameResult> = mutableListOf()
    }

    fun saveGameResults(result: GameResult) {
        require(result.isNotEmpty()) { "Result cannot be empty" }
        require(result.all { it.id in TeamService.teamsStorage.keys }) { "Invalid team IDs" }
        gameHistory.add(result)
        saveGameHistory()
    }

    private fun saveGameHistory() {
        val json = Json.encodeToString(gameHistory.map { SerializableGameResult(it.map { t -> SerializableTeam(t.id, t.points) }) })
        File("gameHistory.json").writeText(json)
    }

    fun loadGameHistory() {
        val file = File("gameHistory.json")
        if (file.exists()) {
            val history = Json.decodeFromString<List<SerializableGameResult>>(file.readText())
            gameHistory.addAll(history.map { it.teams.map { t -> Team(t.id, t.points) } })
        }
    }

    fun getAllGameResults(): List<GameResult> = gameHistory.reversed()
}
