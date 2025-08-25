package futurodev.moviedev.MovieDev.repositories;

import futurodev.moviedev.MovieDev.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Query para retornar filmes por gênero
    List<Movie> findByGenre(String genre);

    // Query para retornar filmes por ano de lançamento
    List<Movie> findByReleaseYear(Integer releaseYear);

    // Query para retornar filmes por classificação
    List<Movie> findByRating(Integer rating);

    // Query para retornar filmes por título (contendo uma palavra específica)
    List<Movie> findByTitleContaining(String title);

    // Query para retornar filmes por gênero e ano de lançamento
    List<Movie> findByGenreAndReleaseYear(String genre, Integer releaseYear);
}