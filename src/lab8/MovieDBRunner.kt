package lab8

import common.FileReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter


internal fun parseMovies(moviesLines: List<String>): List<Movie> {
    return moviesLines
        .drop(1)
        .map { line ->
                // budget;genres;title;rating;release_date;revenue;runtime;cast;director
                val components = line.split(";")


                val budget = components[0].trim().toLong()
                val genres = components[1].trim().split(",").map { it.trim() }
                val title = components[2].trim()
                val rating = components[3].trim().toDouble()
                val releaseDate = LocalDate.parse(components[4].trim())
                val revenue = components[5].trim().toLong()
                val runtimeInMinutes = components[6].trim().toDouble().toInt()

                val actors = components[7].trim().split(",")
                    .filter { it.isNotBlank() }
                    .map { MovieActor(it) }

                val director = MovieDirector(components[5].trim())

                Movie(title, budget, revenue, releaseDate, runtimeInMinutes, director, genres, actors, rating)
        }
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("lab8/movies.csv")
    val movies = parseMovies(moviesCSVFile)

    val movieDBApi : MovieDBApi = MovieDB(movies)
}