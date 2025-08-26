package futurodev.moviedev.MovieDev.services;

import futurodev.moviedev.MovieDev.exceptions.ConflictException;
import futurodev.moviedev.MovieDev.exceptions.ResourceNotFoundException;
import futurodev.moviedev.MovieDev.models.Movie;
import futurodev.moviedev.MovieDev.repositories.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private List<Movie> movies;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Create
    @Transactional
    public Movie addMovie(Movie movie) {
        boolean exists = movieRepository.existsByTitleAndReleaseYearAndRatingAndGenre(movie.getTitle(), movie.getReleaseYear(), movie.getRating(), movie.getGenre());
        if (exists)
            throw new ConflictException("Filme já existe no banco de dados.");

        return movieRepository.save(movie);
    }

    // Delete
    @Transactional
    public String deleteAllMovies() {
        movieRepository.deleteAll();
        return "Todos os filmes foram deletados com sucesso!";
    }

    // Delete by ID
    @Transactional
    public String deleteMovieById(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return "Filme com ID " + id + " deletado com sucesso!";
        } else {
            throw new ResourceNotFoundException("Filme com ID " + id + " não encontrado.");
        }
    }

    public long countMovies() {
        return movieRepository.count();
    }

    // Update
    @Transactional
    public String updateMovie(Long id, Movie updatedMovie) {
        return movieRepository.findById(id).map(movie -> {
            movie.setTitle(updatedMovie.getTitle());
            movie.setGenre(updatedMovie.getGenre());
            movie.setReleaseYear(updatedMovie.getReleaseYear());
            movie.setRating(updatedMovie.getRating());
            movieRepository.save(movie);
            return "Filme com ID " + id + " atualizado com sucesso!";
        }).orElseThrow(() -> new ResourceNotFoundException("Filme com ID " + id + " não encontrado."));
    }

    // Read
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Get movies by genre
    public List<Movie> getMoviesByGenre(String genre) {
        movies = movieRepository.findByGenre(genre);
        if (movies.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum filme encontrado para o gênero: " + genre);
        }
        return movies;
    }

    // Get movies by release year
    public List<Movie> getMoviesByReleaseYear(Integer releaseYear) {
        movies = movieRepository.findByReleaseYear(releaseYear);
        if (movies.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum filme encontrado para o ano de lançamento: " + releaseYear);
        }
        return movies;
    }

    // Get movies by rating
    public List<Movie> getMoviesByRating(Integer rating) {
        movies = movieRepository.findByRating(rating);
        if (movies.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum filme encontrado para a classificação: " + rating);
        }
        return movies;
    }

    // Get movies by title containing a specific word
    public List<Movie> getMoviesByTitleContaining(String title) {
        movies =  movieRepository.findByTitleContaining(title);
        if (movies.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum filme encontrado contendo no título: " + title);
        }
        return movies;
    }

    // Get movies by genre and release year
    public List<Movie> getMoviesByGenreAndReleaseYear(String genre, Integer releaseYear) {
        movies =  movieRepository.findByGenreAndReleaseYear(genre, releaseYear);
        if (movies.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum filme encontrado para o gênero: " + genre + " e ano de lançamento: " + releaseYear);
        }
        return movies;
    }

}