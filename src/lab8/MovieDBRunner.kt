package lab8

import common.FileReader
import java.time.LocalDate


private fun parseMovies(moviesLines: List<String>): List<Movie> {
    return moviesLines
        .drop(1)
        .map { line ->
            val parts = line.split(";")
            val budget = parts[0].trim().toLong()
            val genres = parts[1].trim().split(",").map { it.trim() }
            val title = parts[2].trim()
            val rating = parts[3].trim().toDouble()

            val relaseDate = LocalDate.parse(parts[4])
            val revenue = parts[5].trim().toLong()
            val runtime = parts[6].trim().toDouble().toInt()
            val actors = parts[7].trim().split(",")
                .filter { it.isNotBlank() }
                .map { MovieActor(it.trim()) }
            val director = MovieDirector(parts[8].trim())

            Movie(title, budget, revenue, relaseDate, runtime, director, genres, actors, rating)
        }
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("lab8/movies.csv")
    val movies = parseMovies(moviesCSVFile)

    val movieDBApi : MovieDBApi = MovieDB(movies)
}