package lab8

import common.FileReader
import lab7.Fixture
import lab7.Match
import lab7.Team
import java.time.LocalDate


fun parseMovies(moviesLines: List<String>): List<Movie> {
    val dataLines = moviesLines.subList(1, moviesLines.size)
    val listOfMovie = mutableListOf<Movie>()

    dataLines.forEach {
        val data = it.split(";").map { it.trim() }
        val budget = data[0].toLong()
        val genres = data[1].split(",").map { it.trim() }
        val title = data[2]
        val rating = data[3].toDouble()
        val releaseDate = LocalDate.parse(data[4])
        val revenue = data[5].toLong()
        val runtimeInMinutes = data[6].toDouble().toInt()
        //Because at the end of list of all actors in .csv file, there is ',' and blank space
        //So we need to trim string and filter all empty strings
        val actors = data[7].split(",").map { it.trim() }
            .filter { it.isNotEmpty() }.map { MovieActor(it) }
        val director = MovieDirector(data[8])

        listOfMovie.add(Movie(title, budget, revenue, releaseDate, runtimeInMinutes, director, genres, actors, rating))
    }

    return listOfMovie
}

fun main() {
    val moviesCSVFile = FileReader.readFileInResources("lab8/movies.csv")
    val movies = parseMovies(moviesCSVFile)

    val movieDBApi : MovieDBApi = MovieDB(movies)
}