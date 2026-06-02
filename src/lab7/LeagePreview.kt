package lab7

import common.FileReader

internal fun parseFixtures(fixturesText: List<String>): List<Fixture> {
    return fixturesText
        .drop(1)
        .filter { it.isNotBlank() }
        .map { line ->
            val parts = line.split(",")
            val fixtureId = parts[0].toInt()
            val homeTeamName = parts[2].trim()
            val awayTeamName = parts[4].trim()

            val scoreParts = parts[3].split("-")
            val homeScore = scoreParts[0].trim().toInt()
            val awayScore = scoreParts[1].trim().toInt()

            val match = Match(
                homeTeam = Team(homeTeamName),
                awayTeam = Team(awayTeamName),
                homeTeamScore = homeScore,
                awayTeamScore = awayScore
            )

            fixtureId to match
        }
        .groupBy({ it.first }, { it.second })
        .map { (id, matches) -> Fixture(id, matches) }
}

fun main() {
    val fixturesText = FileReader.readFileInResources("/lab7/fixtures.csv")
    val fixtures: List<Fixture> = parseFixtures(fixturesText)
    val teams =
        fixtures.flatMap { fixture -> fixture.matches.flatMap { match -> listOf(match.homeTeam, match.awayTeam) } }
            .distinct()

    val league: LeagueApi = League(teams, fixtures)
    league.displayLeagueTable()

    league.displayLeagueTableAtFixture(13)
}