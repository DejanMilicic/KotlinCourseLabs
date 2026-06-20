package lab7

import common.FileReader

internal fun parseFixtures(fixturesText: List<String>): List<Fixture> {
    return fixturesText.drop(1)
        .map { line ->
            val fields = line.split(",")
            val fixtureId = fields[0].toInt()

            val homeTeam = fields[2]
            val awayTeam = fields[4]

            val (homeScore, awayScore) = fields[3].split("-").map { it.toInt() }
            fixtureId to Match(
                homeTeam = Team(homeTeam),
                awayTeam = Team(awayTeam),
                homeTeamScore = homeScore,
                awayTeamScore = awayScore
            )
        }.groupBy(keySelector = { it.first }, valueTransform = { it.second })
        .toSortedMap()
        .map { (fixtureId, matches) ->
            Fixture(
                fixtureId = fixtureId,
                matches = matches
            )
        }
}

fun main() {
    val fixturesText = FileReader.readFileInResources("/lab7/fixtures.csv")
    val fixtures: List<Fixture> = parseFixtures(fixturesText)
    val teams = fixtures.flatMap { it.matches }
        .flatMap { listOf(it.homeTeam, it.awayTeam) }
        .distinct()

    // Create league object
    val league: LeagueApi = LeagueApiImpl(teams, fixtures)
    league.displayLeagueTable()

    league.displayLeagueTableAtFixture(13)
}