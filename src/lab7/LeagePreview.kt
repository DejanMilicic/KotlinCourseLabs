package lab7

import common.FileReader

internal fun parseFixtures(fixturesText: List<String>): List<Fixture> {
    return fixturesText.drop(1)
        .map { line ->
            val parts = line.split(",")
            val round = parts[0].toInt()
            val homeTeam = Team(parts[2])
            val (homeScore, awayScore) = parts[3].split("-").map { it.toInt() }
            val awayTeam = Team(parts[4])
            round to Match(homeTeam, awayTeam, homeScore, awayScore)
        }
        .groupBy({ it.first }, { it.second })
        .map { (round, matches) -> Fixture(round, matches) }
        .sortedBy { it.fixtureId }
}

fun main() {
    val fixturesText = FileReader.readFileInResources("/lab7/fixtures.csv")
    val fixtures: List<Fixture> = parseFixtures(fixturesText)
    val teams = fixtures.flatMap { it.matches.flatMap { m -> listOf(m.homeTeam, m.awayTeam) } }.toSet().toList()

    // Create league object
    val league: LeagueApi = League(teams, fixtures)
    league.displayLeagueTable()

    league.displayLeagueTableAtFixture(13)
}