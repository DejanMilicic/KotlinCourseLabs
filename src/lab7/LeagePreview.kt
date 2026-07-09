package lab7

import common.FileReader

/**
 * Parses fixtures CSV text into a list of [Fixture].
 *
 * CSV line format (header auto-skipped):
 * Round,Date,Team 1,FT,Team 2
 * e.g. "1,Sat Sep 12 2020,Fulham,0-3,Arsenal"
 * where FT is "homeScore-awayScore".
 */
internal fun parseFixtures(fixturesText: List<String>): List<Fixture> {
    val matchesByFixtureId = linkedMapOf<Int, MutableList<Match>>()

    fixturesText
        .filter { it.isNotBlank() }
        .forEach { line ->
            val columns = line.split(",").map { it.trim() }
            val fixtureId = columns[0].toIntOrNull() ?: return@forEach // skip header/invalid lines

            val homeTeamName = columns[2]
            val score = columns[3]
            val awayTeamName = columns[4]

            val (homeScore, awayScore) = score.split("-").map { it.toInt() }

            val match = Match(
                homeTeam = Team(homeTeamName),
                awayTeam = Team(awayTeamName),
                homeTeamScore = homeScore,
                awayTeamScore = awayScore
            )

            matchesByFixtureId.getOrPut(fixtureId) { mutableListOf() }.add(match)
        }

    return matchesByFixtureId.map { (fixtureId, matches) -> Fixture(fixtureId, matches) }
}

fun main() {
    val fixturesText = FileReader.readFileInResources("/lab7/fixtures.csv")
    val fixtures: List<Fixture> = parseFixtures(fixturesText)
    val teams: List<Team> = fixtures.flatMap { it.matches }
        .flatMap { listOf(it.homeTeam, it.awayTeam) }
        .distinct()

    // Create league object
    val league: LeagueApi = League(teams, fixtures)
    league.displayLeagueTable()

    league.displayLeagueTableAtFixture(13)
}