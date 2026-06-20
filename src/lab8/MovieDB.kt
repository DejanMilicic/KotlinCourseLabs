package lab8

class MovieDB(private val movies: List<Movie>) : MovieDBApi {
    override fun getAllMoviesByActor(actor: MovieActor): List<Movie> {
        return movies.filter { actor in it.actors }
    }

    override fun getMoviesWithBiggestProfit(numOfMovies: Int): List<Movie> {
        return movies.sortedByDescending { it.revenue - it.budget }.take(numOfMovies)
    }

    override fun getBestRatedMovieByActor(actor: MovieActor): Movie? {
        return getAllMoviesByActor(actor).maxByOrNull { it.rating }
    }

    override fun getAllMoviesByYear(year: Int): List<Movie> {
        return movies.filter { it.releaseDate.year == year }
    }

    override fun getAllMoviesByGenre(genre: String): List<Movie> {
        return movies.filter { genre in it.genres }
    }

    override fun getBestRatedMovies(numOfMovies: Int): List<Movie> {
        return movies.sortedByDescending { it.rating }
            .take(numOfMovies)
    }

    override fun getDirectorWithMostMoviesDirected(): MovieDirector {
        return movies.groupingBy { it.director }
            .eachCount()
            .maxBy { it.value }
            .key
    }

    override fun getActorsWithMostCostarredMovies(): List<Pair<MovieActor, MovieActor>> {
        val pairCounts = mutableMapOf<Pair<MovieActor, MovieActor>, Int>()

        for (movie in movies) {
            val actors = movie.actors.sortedBy { it.name }
            for (i in actors.indices) {
                for (j in i + 1 until actors.size) {
                    val pair = Pair(actors[i], actors[j])
                    pairCounts[pair] = pairCounts.getOrDefault(pair, 0) + 1
                }
            }
        }

        val maxCostars = pairCounts.values.maxOrNull() ?: 0
        return pairCounts.filter { it.value == maxCostars }.keys.toList()
    }

}