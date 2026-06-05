package lab7

import common.FileReader

internal fun parseFixtures(fixturesText: List<String>): List<Fixture> {
    val dataLines = fixturesText.subList(1, fixturesText.size)
    val matchesMap = mutableMapOf<Int, MutableList<Match>>()

    dataLines.forEach {
        val data = it.split(",")
        val fixtureId = data[0].toInt()
        val matchesList = matchesMap.getOrPut(fixtureId) { mutableListOf() }
        val team1 = Team(data[2])
        val team2 = Team(data[4])
        val scores = data[3].split("-").map { it.toInt() }
        matchesList.add(Match(team1, team2, scores[0], scores[1]))
    }

    return matchesMap.map { Fixture(it.key, it.value) }
}



fun main() {
    val fixturesText = FileReader.readFileInResources("/lab7/fixtures.csv")
    val fixtures: List<Fixture> = parseFixtures(fixturesText)
    val teams = fixtures
        .flatMap { it.matches }
        .flatMap { listOf(it.homeTeam, it.awayTeam) }
        .distinct()

    // Create league object
    val league: LeagueApi = League(teams, fixtures)
    league.displayLeagueTable()

    league.displayLeagueTableAtFixture(13)
}