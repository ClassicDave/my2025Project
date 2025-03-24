package jetbrains.kotlin.course.alias

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AliasApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<AliasApplication>(*args)
    val teamService = TeamService()
    val gameResultsService = GameResultsService()
    
    // Load previous data
    teamService.loadTeams()
    gameResultsService.loadGameHistory()
    
    println("Previous game data loaded successfully!")
}

