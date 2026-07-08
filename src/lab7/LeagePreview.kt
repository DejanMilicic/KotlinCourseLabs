package lab7

import common.FileReader

internal fun parseFixtures(fixturesText: List<String>): List<Fixture> {
    return fixturesText
        .drop(1)
        .filter { it.isNotBlank() }
        .map { row ->
            val parts = row.split(",")
            val fixtureId = parts[0].trim().toInt()
            val homeTeam = Team(parts[2].trim())
            val (homeScore, awayScore) = parts[3].trim().split("-").map { it.toInt() }
            val awayTeam = Team(parts[4].trim())

            fixtureId to Match(
                homeTeam = homeTeam,
                awayTeam = awayTeam,
                homeTeamScore = homeScore,
                awayTeamScore = awayScore
            )
        }
        .groupBy({ it.first }, { it.second })
        .toSortedMap()
        .map { (fixtureId, matches) -> Fixture(fixtureId, matches) }
}

fun main() {
    val fixturesText = FileReader.readFileInResources("/lab7/fixtures.csv")
    val fixtures: List<Fixture> = parseFixtures(fixturesText)
    val teams = fixtures
        .flatMap { fixture -> fixture.matches.flatMap { match -> listOf(match.homeTeam, match.awayTeam) } }
        .distinct()

    // Create league object
    val league: LeagueApi = League(teams, fixtures)
    league.displayLeagueTable()

    league.displayLeagueTableAtFixture(13)
}