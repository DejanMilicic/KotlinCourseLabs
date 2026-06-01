package lab8

import common.FileReader
import java.time.LocalDate

fun parseMovies(moviesLines: List<String>): List<Movie> {
    return moviesLines.drop(1)
        .map { line ->
            val columns = line.split(";")
            val budget = columns[0].toLong()
            val genres = columns[1].split(",").map { it.trim() }
            val title = columns[2]
            val rating = columns[3].toDouble()
            val releaseDate = LocalDate.parse(columns[4])
            val revenue = columns[5].toLong()
            val runtimeInMinutes = columns[6].toDouble().toInt()
            val actors = columns[7]
                .split(",")
                .map { it.trim() }
                .filter { it.isNotBlank() }
                .map { MovieActor(it) }

            val director = MovieDirector(columns[8].trim())

            Movie(title, budget, revenue, releaseDate, runtimeInMinutes, director, genres, actors, rating)
        }
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("lab8/movies.csv")
    val movies = parseMovies(moviesCSVFile)

    val movieDBApi : MovieDBApi = MovieDB(movies)
}