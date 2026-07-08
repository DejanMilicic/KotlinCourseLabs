package lab7

import common.FileReader

internal fun parseFixtures(fixturesText: List<String>): List<Fixture> {
    val fixtures: MutableList<Fixture> = mutableListOf()
    val matches = hashMapOf<Int, MutableList<Match>>()
    for (i in 1 until fixturesText.size) {
        val data = fixturesText[i].split(",")
        val team1 = Team(data[2])
        val team2 = Team(data[4])
        val score = data[3].split("-")
        val match = Match(team1, team2, score[0].toInt(), score[1].toInt())
        val id = data[0].toInt()
        if (matches[id] != null) {
            matches[id]!!.add(match)
        } else {
            matches[id] = mutableListOf(match)
        }
    }
    for (x in matches) {
        fixtures.add(Fixture(x.key, x.value))
    }
    return fixtures
}

internal fun parseTeams(fixtures: List<Fixture>): List<Team> {
    val teams = hashSetOf<Team>()
    for (fixture in fixtures) {
        for (match in fixture.matches) {
            teams.add(match.homeTeam)
            teams.add(match.awayTeam)
        }
    }
    return teams.toList()
}

fun main() {
    val fixturesText = FileReader.readFileInResources("/lab7/fixtures.csv")
    val fixtures: List<Fixture> = parseFixtures(fixturesText)
    val teams = parseTeams(fixtures)

    // Create league object
    val league: LeagueApi = League(teams, fixtures)
    league.displayLeagueTable()

    league.displayLeagueTableAtFixture(13)
}