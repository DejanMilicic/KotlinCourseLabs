package lab8

import common.FileReader
import java.time.LocalDate


fun parseMovies(moviesLines: List<String>): List<Movie> {
    return moviesLines.drop(1).map { line ->
        val column = line.split(";")
        val buget = column[0].toLong()
        val ganres = column[1].split(",").map { it.trim() }
        val title = column[2]
        val rating = column[3].toDouble()
        val releaseDate = LocalDate.parse(column[4])
        val revenue = column[5].toLong()
        val runtime = column[6].toDouble().toInt()
        val cast = column[7].split(",").filter { it.isNotBlank() }.map { MovieActor(it.trim()) }
        val director = MovieDirector(column[8].trim())

        Movie(
            title = title,
            budget = buget,
            revenue = revenue,
            releaseDate = releaseDate,
            runtimeInMinutes = runtime,
            director = director,
            genres = ganres,
            actors = cast,
            rating = rating

        )
    }
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("lab8/movies.csv")
    val movies = parseMovies(moviesCSVFile)

    val movieDBApi: MovieDBApi = MovieDB(movies)
}