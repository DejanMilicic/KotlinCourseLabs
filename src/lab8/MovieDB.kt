package lab8

class MovieDB(private val movies: List<Movie>) : MovieDBApi {

    override fun getAllMoviesByActor(actor: MovieActor): List<Movie> {
        return movies.filter { actor in it.actors }
    }

    override fun getMoviesWithBiggestProfit(numOfMovies: Int): List<Movie> {
        return movies.sortedByDescending { it.revenue - it.budget }.take(numOfMovies)
    }

    override fun getBestRatedMovieByActor(actor: MovieActor): Movie? {
        return movies.filter { actor in it.actors }.maxByOrNull { it.rating }
    }

    override fun getAllMoviesByYear(year: Int): List<Movie> {
        return movies.filter { it.releaseDate.year == year }
    }

    override fun getAllMoviesByGenre(genre: String): List<Movie> {
        return movies.filter { genre in it.genres }
    }

    override fun getBestRatedMovies(numOfMovies: Int): List<Movie> {
        return movies.sortedByDescending { it.rating }.take(numOfMovies)
    }

    override fun getDirectorWithMostMoviesDirected(): MovieDirector {
        return movies.groupingBy { it.director }.eachCount().maxBy { it.value }.key
    }

    override fun getActorsWithMostCostarredMovies(): List<Pair<MovieActor, MovieActor>> {
        val pairCounts = mutableMapOf<Pair<MovieActor, MovieActor>, Int>()

        movies.forEach { movie ->
            val actors = movie.actors
            for (i in actors.indices) {
                for (j in i + 1 until actors.size) {
                    val pair = if (actors[i].name <= actors[j].name) {
                        actors[i] to actors[j]
                    } else {
                        actors[j] to actors[i]
                    }
                    pairCounts[pair] = (pairCounts[pair] ?: 0) + 1
                }
            }
        }

        if (pairCounts.isEmpty()) return emptyList()

        val maxCount = pairCounts.values.max()
        return pairCounts.filter { it.value == maxCount }.keys.toList()
    }
}