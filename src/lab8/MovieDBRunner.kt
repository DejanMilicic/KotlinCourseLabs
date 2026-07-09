package lab8

import common.FileReader
import java.time.LocalDate


fun parseMovies(moviesLines: List<String>): List<Movie> {
    return moviesLines.drop(1).map { line ->
        val parts = line.split(";")
        val budget = parts[0].toLong()
        val genres = parts[1].split(",")
        val title = parts[2]
        val rating = parts[3].toDouble()
        val releaseDate = LocalDate.parse(parts[4])
        val revenue = parts[5].toLong()
        val runtimeInMinutes = parts[6].toDouble().toInt()
        val actors = parts[7].split(",").filter { it.isNotBlank() }.map { MovieActor(it.trim()) }
        val director = MovieDirector(parts[8])

        Movie(title, budget, revenue, releaseDate, runtimeInMinutes, director, genres, actors, rating)
    }
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("lab8/movies.csv")
    val movies = parseMovies(moviesCSVFile)

    val movieDBApi: MovieDBApi = MovieDB(movies)
}