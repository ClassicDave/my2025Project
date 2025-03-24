package jetbrains.kotlin.course.alias.team

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

import jetbrains.kotlin.course.alias.util.Identifier
import jetbrains.kotlin.course.alias.util.IdentifierFactory
import org.springframework.stereotype.Service


@Serializable
data class SerializableTeam(val id: Identifier, val points: Int)

@Service
class TeamService {
    private val identifierFactory = IdentifierFactory()

    companion object {
        val teamsStorage: MutableMap<Identifier, Team> = mutableMapOf()
    }

    fun generateTeamsForOneRound(teamCount: Int): List<Team> {
        val teams = List(teamCount) { Team(identifierFactory.uniqueIdentifier()) }
        teams.forEach { teamsStorage[it.id] = it }
        return teams
    }
    private fun saveTeams() {
        val json = Json.encodeToString(teamsStorage.values.map { SerializableTeam(it.id, it.points) })
        File("teams.json").writeText(json)
    }

    fun loadTeams() {
        val file = File("teams.json")
        if (file.exists()) {
            val teams = Json.decodeFromString<List<SerializableTeam>>(file.readText())
            teamsStorage.putAll(teams.associate { it.id to Team(it.id, it.points) })
        }
    }
}


