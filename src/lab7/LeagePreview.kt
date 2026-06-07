package lab7

import common.FileReader

internal fun parseFixtures(fixturesText: List<String>): List<Fixture> {
    return fixturesText
        .drop(1)
        .map { line ->
            val parts = line.split(",")
            val fixtureId = parts[0].trim().toInt()
            val homeTeam = Team(parts[2].trim())
            val awayTeam = Team(parts[4].trim())
            val score = parts[3].trim().split("-")
            val homeTeamScore = score[0].toInt()
            val awayTeamScore = score[1].toInt()

            fixtureId to Match(
                homeTeam, awayTeam, homeTeamScore, awayTeamScore
            )
        }
        .groupBy(
            keySelector = { it.first },
            valueTransform = { it.second }
        )
        .map { (fixtureId, matches) -> Fixture(fixtureId, matches) }
        .sortedBy { it.fixtureId }
}

fun main() {
    val fixturesText = FileReader.readFileInResources("/lab7/fixtures.csv")
    val fixtures: List<Fixture> = parseFixtures(fixturesText)
    val teams = fixtures
        .flatMap { fixture ->
            fixture.matches.flatMap {
                listOf(it.homeTeam, it.awayTeam)
            }
        }
        .distinct()

    // Create league object
    val league: LeagueApi = League(teams, fixtures)
    league.displayLeagueTable()

    league.displayLeagueTableAtFixture(13)
}