package futurodev.moviedev.MovieDev.services;

import futurodev.moviedev.MovieDev.models.Movie;
import futurodev.moviedev.MovieDev.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Create
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    // Delete
    public String deleteAllMovies() {
        movieRepository.deleteAll();
        return "Todos os filmes foram deletados com sucesso!";
    }

    // Delete by ID
    public String deleteMovieById(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return "Filme com ID " + id + " deletado com sucesso!";
        } else {
            return "Filme com ID " + id + " não encontrado.";
        }
    }

    public long countMovies() {
        return movieRepository.count();
    }

    // Update
    public String updateMovie(Long id, Movie updatedMovie) {
        return movieRepository.findById(id).map(movie -> {
            movie.setTitle(updatedMovie.getTitle());
            movie.setGenre(updatedMovie.getGenre());
            movie.setReleaseYear(updatedMovie.getReleaseYear());
            movie.setRating(updatedMovie.getRating());
            movieRepository.save(movie);
            return "Filme com ID " + id + " atualizado com sucesso!";
        }).orElse("Filme com ID " + id + " não encontrado.");
    }

    // Read
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Get movies by genre
    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    // Get movies by release year
    public List<Movie> getMoviesByReleaseYear(Integer releaseYear) {
        return movieRepository.findByReleaseYear(releaseYear);
    }

    // Get movies by rating
    public List<Movie> getMoviesByRating(Integer rating) {
        return movieRepository.findByRating(rating);
    }

    // Get movies by title containing a specific word
    public List<Movie> getMoviesByTitleContaining(String title) {
        return movieRepository.findByTitleContaining(title);
    }

    // Get movies by genre and release year
    public List<Movie> getMoviesByGenreAndReleaseYear(String genre, Integer releaseYear) {
        return movieRepository.findByGenreAndReleaseYear(genre, releaseYear);
    }

}
