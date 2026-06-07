package lab7

internal interface LeagueApi {
    /**
     * Represents a list of teams participating in a league.
     *
     * @property teams The list of teams.
     */
    val teams: List<Team>
    /**
     * Returns the list of [LeagueTableEntry] where every team result of the season is aggregated.
     * The League table is sorted by points descending. If more than one team has the same number of points,
     * sort them by goal difference.
     */
    val leagueTable: List<LeagueTableEntry>

    /**
     * Returns the team that won the league.
     *
     * @property leagueWinner The team that won the league.
     * The Team won the league if it has the most points in the league.
     * If two or more teams have a same number of points, the team with the best goal difference among them wins the league.
     */
    val leagueWinner: Team

    /**
     * Returns the team with the most wins in the league.
     */
    val teamWithMostWins: Team

    /**
     * Returns the team with the most draws in the league.
     */
    val teamWithMostDraws: Team

    /**
     * Returns the team with the most loses in a league.
     */
    val teamWithMostLoses: Team

    /**
     * Returns a team in the league with the best goal difference.
     */
    val teamWithBestGoalDifference: Team

    /**
     * Calculates the teams with the best defense based on the number of goals they have conceded.
     *
     * @param numOfTeams The number of teams to include in the result.
     * @return The list of teams with the best defense, sorted in ascending order based on the number of goals conceded.
     */
    fun teamsWithBestDefence(numOfTeams: Int): List<Team>

    /**
     * Calculates the teams with the best offense based on the number of goals they have scored.
     *
     * @param numOfTeams The number of teams to include in the result.
     * @return The list of teams with the best offense, sorted in descending order based on the number of goals scored.
     */
    fun teamsWithBestOffense(numOfTeams: Int): List<Team>

    /**
     * Calculates the number of goals that a team has scored against a specific opponent team.
     *
     * @param scorerTeam The team that has scored the goals.
     * @param against The opponent team.
     * @return The number of goals that the scorer team has scored against the opponent team.
     */
    fun numOfGoalsTeamScoredAgainst(scorerTeam: Team, against: Team): Int

    /**
     * Calculates the number of goals that a conceded team has conceded against a specific opponent team.
     *
     * @param concededTeam The team that has conceded the goals.
     * @param against The opponent team.
     * @return The number of goals that the scorer team has conceded against the opponent team.
     */
    fun numOfGoalsTeamConcededAgainst(concededTeam: Team, against: Team): Int

    /**
     * Display the league table after fixture with [fixtureId] was played.
     * Table should be displayed in the same format as in a method [displayLeagueTable].
     */
    fun displayLeagueTableAtFixture(fixtureId: Int)

    /**
     * Display league table with all fixtures are played.
     * League table should be printed in the following format:
     * ```
     * P | Team name | Games Played | Wins | Draws | Loses | GS | GC | Total Points
     * 1. Manchester City 38 27 5 6 83 32 86
     * 2. Manchester Utd 38 21 11 6 73 44 74
     * 3. Liverpool 38 20 9 9 68 42 69
     * 4. Chelsea 38 19 10 9 58 36 67
     * 5. Leicester City 38 20 6 12 68 50 66
     * 6. West Ham 38 19 8 11 62 47 65
     * 7. Tottenham 38 18 8 12 68 45 62
     * 8. Arsenal 38 18 7 13 55 39 61
     * 9. Leeds United 38 18 5 15 62 54 59
     * 10. Everton 38 17 8 13 47 48 59
     * 11. Aston Villa 38 16 7 15 55 46 55
     * 12. Newcastle Utd 38 12 9 17 46 62 45
     * 13. Wolves 38 12 9 17 36 52 45
     * 14. Crystal Palace 38 12 8 18 41 66 44
     * 15. Southampton 38 12 7 19 47 68 43
     * 16. Brighton 38 9 14 15 40 46 41
     * 17. Burnley 38 10 9 19 33 55 39
     * 18. Fulham 38 5 13 20 27 53 28
     * 19. West Brom 38 5 11 22 35 76 26
     * 20. Sheffield Utd 38 7 2 29 20 63 23
     * ```
     * where columns are ordered as following:
     * `P` - is a position on the board
     * `Team name` - name of the team
     * `Games Played` - total games played by team
     * `Wins` - total wins by team
     * `Draws` - total draws by team
     * `Loses` - total loses by team
     * `GS` - total goals scored by team
     * `GC` - total goals conceded by team
     * `Total Points` - total points won by team
     */
    fun displayLeagueTable()
}

/**
 * Task: Implement class [League] that implements [LeagueApi] interface. Class [League] has two properties:
 * @property teams - teams in the League.
 * @property fixtures - League fixtures with a set of games played that round.
 *
 * Task: In class init block, validate parameters of the class.
 * Parameters are valid if all the fixtures contain only teams from the [teams] list,
 * and [teams] are all mentioned in the [fixtures] list.
 */

internal class League(
    override val teams: List<Team>,
    val fixtures: List<Fixture>
) : LeagueApi {

    init {
        val fixtureTeams = fixtures.flatMap { it.matches }.flatMap { listOf(it.homeTeam, it.awayTeam) }.toSet()
        require(fixtureTeams == teams.toSet()) {
            "Teams in fixtures don't match the provided teams list"
        }
    }

    private fun fixturesUpTo(fixtureId: Int) = fixtures.filter { it.fixtureId <= fixtureId }

    private fun buildLeagueTable(fixturesList: List<Fixture>): List<LeagueTableEntry> {
        val matches = fixturesList.flatMap { it.matches }
        return teams.map { team ->
            val homeMatches = matches.filter { it.homeTeam == team }
            val awayMatches = matches.filter { it.awayTeam == team }

            val wins = homeMatches.count { it.homeTeamScore > it.awayTeamScore } +
                    awayMatches.count { it.awayTeamScore > it.homeTeamScore }
            val loses = homeMatches.count { it.homeTeamScore < it.awayTeamScore } +
                    awayMatches.count { it.awayTeamScore < it.homeTeamScore }
            val draws = homeMatches.count { it.homeTeamScore == it.awayTeamScore } +
                    awayMatches.count { it.homeTeamScore == it.awayTeamScore }

            val scored = homeMatches.sumOf { it.homeTeamScore } + awayMatches.sumOf { it.awayTeamScore }
            val conceded = homeMatches.sumOf { it.awayTeamScore } + awayMatches.sumOf { it.homeTeamScore }

            LeagueTableEntry(team, wins + loses + draws, wins, loses, draws, scored, conceded)
        }.sortedWith(
            compareByDescending<LeagueTableEntry> { it.totalPoints }
                .thenByDescending { it.totalScoredGoals - it.totalConcededGoals }
        )
    }

    override val leagueTable: List<LeagueTableEntry> get() = buildLeagueTable(fixtures)

    override val leagueWinner: Team get() = leagueTable.first().team

    override val teamWithMostWins: Team get() = leagueTable.maxBy { it.wins }.team

    override val teamWithMostDraws: Team get() = leagueTable.maxBy { it.draws }.team

    override val teamWithMostLoses: Team get() = leagueTable.maxBy { it.loses }.team

    override val teamWithBestGoalDifference: Team get() =
        leagueTable.maxBy { it.totalScoredGoals - it.totalConcededGoals }.team

    override fun teamsWithBestDefence(numOfTeams: Int): List<Team> =
        leagueTable.sortedBy { it.totalConcededGoals }.take(numOfTeams).map { it.team }

    override fun teamsWithBestOffense(numOfTeams: Int): List<Team> =
        leagueTable.sortedByDescending { it.totalScoredGoals }.take(numOfTeams).map { it.team }

    override fun numOfGoalsTeamScoredAgainst(scorerTeam: Team, against: Team): Int {
        val matches = fixtures.flatMap { it.matches }
        return matches.filter { it.homeTeam == scorerTeam && it.awayTeam == against }.sumOf { it.homeTeamScore } +
                matches.filter { it.awayTeam == scorerTeam && it.homeTeam == against }.sumOf { it.awayTeamScore }
    }

    override fun numOfGoalsTeamConcededAgainst(concededTeam: Team, against: Team): Int =
        numOfGoalsTeamScoredAgainst(against, concededTeam)

    override fun displayLeagueTableAtFixture(fixtureId: Int) {
        buildLeagueTable(fixturesUpTo(fixtureId)).printTable()
    }

    override fun displayLeagueTable() {
        leagueTable.printTable()
    }

    private fun List<LeagueTableEntry>.printTable() {
        println("P | Team name | Games Played | Wins | Draws | Loses | GS | GC | Total Points")
        forEachIndexed { index, e ->
            println("${index + 1}. ${e.team.name} ${e.totalGamesPlayed} ${e.wins} ${e.draws} ${e.loses} ${e.totalScoredGoals} ${e.totalConcededGoals} ${e.totalPoints}")
        }
    }
}