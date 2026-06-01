package lab7

import common.FileReader

internal fun parseFixtures(fixturesText: List<String>): List<Fixture> {
    return fixturesText
        .drop(1) // skip header
        .groupBy { it.split(",")[0].trim().toInt() }
        .map { (fixtureId, lines) ->
            Fixture(
                fixtureId,
                lines.map { line ->
                    val parts = line.split(",")
                    val homeTeam = Team(parts[2].trim())
                    val awayTeam = Team(parts[4].trim())
                    val score = parts[3].trim().split("-")
                    Match(
                        homeTeam = homeTeam,
                        awayTeam = awayTeam,
                        homeTeamScore = score[0].toInt(),
                        awayTeamScore = score[1].toInt()
                    )
                }
            )
        }
        .sortedBy { it.fixtureId }
}

fun main() {
    val fixturesText = FileReader.readFileInResources("/lab7/fixtures.csv")
    val fixtures = parseFixtures(fixturesText)
    val teams = fixtures.flatMap { it.matches }
        .flatMap { listOf(it.homeTeam, it.awayTeam) }
        .distinct()

    val league: LeagueApi = League(teams, fixtures)
    league.displayLeagueTable()
    league.displayLeagueTableAtFixture(13)
}