package lab7

import common.FileReader

internal fun parseFixtures(fixturesText: List<String>): List<Fixture> {
    return fixturesText.drop(1)
        .map { line ->
            val parts = line.split(",")
            val fixtureId = parts[0].toInt()
            val homeTeam = Team(parts[2].trim())
            val score = parts[3].trim().split("-")
            val awayTeam = Team(parts[4].trim())

            fixtureId to Match(
                homeTeam = homeTeam,
                awayTeam = awayTeam,
                homeTeamScore = score[0].toInt(),
                awayTeamScore = score[1].toInt()
            )
        }
        .groupBy({ it.first }, { it.second })
        .toSortedMap()
        .map { (fixtureId, matches) -> Fixture(fixtureId, matches) }
}

fun main() {
    val fixturesText = FileReader.readFileInResources("/lab7/fixtures.csv")
    val fixtures: List<Fixture> = parseFixtures(fixturesText)
    val teams = fixtures.flatMap { fixture ->
        fixture.matches.flatMap { match -> listOf(match.homeTeam, match.awayTeam) }
    }.distinct()

    // Create league object
    val league: LeagueApi = League(teams, fixtures)
    league.displayLeagueTable()

    league.displayLeagueTableAtFixture(13)
}
