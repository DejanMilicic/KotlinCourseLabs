package lab8

import java.time.LocalDate

/**
 * Task: Write a MovieDB class that implements the MovieDBApi interface.
 *
 * Define a constructor of MovieDB class which accepts the list of movies as parameter.
 *
 * Implement methods defined by MovieDBApi.
 *
 */

class MovieDB(val movieDB: List<Movie>) : MovieDBApi {
    override fun getAllMoviesByActor(actor: MovieActor): List<Movie> {
        return movieDB.filter { it.actors.contains(actor) }
    }

    override fun getMoviesWithBiggestProfit(numOfMovies: Int): List<Movie> {
        return movieDB.sortedByDescending { it.revenue - it.budget }.take(numOfMovies)
    }

    override fun getBestRatedMovieByActor(actor: MovieActor): Movie? {
        return movieDB.filter { it.actors.contains(actor) }.maxByOrNull { it.rating }
    }

    override fun getAllMoviesByYear(year: Int): List<Movie> {
        return movieDB.filter { it.releaseDate.year == year }
    }

    override fun getAllMoviesByGenre(genre: String): List<Movie> {
        return movieDB.filter { it.genres.contains(genre) }
    }

    override fun getBestRatedMovies(numOfMovies: Int): List<Movie> {
        return movieDB.sortedByDescending { it.rating }.take(numOfMovies)
    }

    override fun getDirectorWithMostMoviesDirected(): MovieDirector {
        return movieDB.groupBy { it.director }.maxByOrNull { it.value.size }!!.key
    }

    override fun getActorsWithMostCostarredMovies(): List<Pair<MovieActor, MovieActor>> {
        val pairCount = mutableMapOf<Pair<MovieActor, MovieActor>, Int>()

        for (movie in movieDB) {
            val actor = movie.actors
            for (i in actor.indices) {
                for (j in i + 1 until actor.size) {
                    val actor1 = if (actor[i].name < actor[j].name) actor[i] else actor[j]
                    val actor2 = if (actor[i].name < actor[j].name) actor[j] else actor[i]
                    val pair = Pair(actor1, actor2)
                    pairCount[pair] = (pairCount[pair] ?: 0) + 1
                }
            }
        }
        val max = pairCount.values.maxOrNull() ?: return emptyList()
        return pairCount.filter { it.value == max }.keys.toList()
            .sortedWith(compareBy({ it.first.name }, { it.second.name }))
    }


}