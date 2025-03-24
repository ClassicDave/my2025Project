package jetbrains.kotlin.course.alias.team

import jetbrains.kotlin.course.alias.util.Identifier

data class Team(val id: Identifier, val points: Int = 0) {
    val name: String = "Team#${id + 1}"
}
