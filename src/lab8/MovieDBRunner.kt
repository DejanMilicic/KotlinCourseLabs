package lab8

import common.FileReader
import java.time.LocalDate


fun parseMovies(moviesLines: List<String>): List<Movie> {
    val movies = mutableListOf<Movie>()
    for (i in 1 until moviesLines.size) {
        val data = moviesLines[i].split(";")
        val budget = data[0].toLong()
        val genres = data[1].split(",").map { it.trim() }
        val title = data[2]
        val rating = data[3].toDouble()
        val releaseDate: LocalDate = LocalDate.parse(data[4])
        val revenue = data[5].toLong()
        val runTimeInMinutes = data[6].toDouble()
        val director = MovieDirector(data[8])
        val actorsStr = data[7].split(",")
        val actors = mutableListOf<MovieActor>()
        for (actorStr in actorsStr) {
            if (actorStr.trim().isNotEmpty())
                actors.add(MovieActor(actorStr.trim()))
        }
        movies.add(Movie(title, budget, revenue, releaseDate, runTimeInMinutes, director, genres, actors, rating))
    }
    return movies
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("lab8/movies.csv")
    val movies = parseMovies(moviesCSVFile)

    val movieDBApi: MovieDBApi = MovieDB(movies)
}