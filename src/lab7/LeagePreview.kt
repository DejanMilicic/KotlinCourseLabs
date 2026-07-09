package lab7

import common.FileReader

internal fun parseFixtures(fixturesText: List<String>): List<Fixture> {
    val teams = HashMap<String, Team>()

    return fixturesText

        .drop(1)
        .map { fixtureText ->
            val fixtureData = fixtureText.split(",")

            val fixtureId = fixtureData[0].toInt()
            val homeTeam = teams.getOrPut(fixtureData[2]) { Team(fixtureData[2]) }
            val awayTeam = teams.getOrPut(fixtureData[4]) { Team(fixtureData[4]) }
            val (homeTeamScore, awayTeamScore) = fixtureData[3].trim()
                .split("-")
                .let { scores -> scores.first().toInt() to scores[1].toInt() }

            fixtureId to Match(homeTeam, awayTeam, homeTeamScore, awayTeamScore)
        }.fold(mutableMapOf<Int, List<Match>>()) { fixtureIdsToMatches, (fixtureId: Int, match: Match) ->
            val matches = fixtureIdsToMatches.getOrDefault(fixtureId, emptyList())

            fixtureIdsToMatches[fixtureId] = matches + match

            fixtureIdsToMatches
        }.entries.map { (fixtureId, matches) -> Fixture(fixtureId, matches) }
}

fun main() {
    val fixturesText = FileReader.readFileInResources("/lab7/fixtures.csv")
    val fixtures: List<Fixture> = parseFixtures(fixturesText)
    val teams = fixtures.flatMap { it.matches }
        .flatMap { listOf(it.homeTeam, it.awayTeam) }
        .distinct()

    // Create league object
    val league: LeagueApi = League(teams, fixtures)
    league.displayLeagueTable()

    league.displayLeagueTableAtFixture(13)
}