package futurodev.moviedev.MovieDev.controllers;

import futurodev.moviedev.MovieDev.dtos.RequestMovie;
import futurodev.moviedev.MovieDev.dtos.ResponseMovie;
import futurodev.moviedev.MovieDev.models.Movie;
import futurodev.moviedev.MovieDev.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllMovies() {
        String message = movieService.deleteAllMovies();
        return ResponseEntity.ok().body(Map.of("message", message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovieById(@PathVariable Long id) {
        String message = movieService.deleteMovieById(id);
        return ResponseEntity.ok().body(Map.of("message", message));
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countMovies() {
        long count = movieService.countMovies();
        return ResponseEntity.ok(Map.of("count", count));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateMovie(@PathVariable Long id, @RequestBody @Valid RequestMovie updatedMovieDTO) {
        String message = movieService.updateMovie(id, updatedMovieDTO.toEntity());
        return ResponseEntity.ok(Map.of("message", message));
    }

    @PostMapping
    public ResponseMovie addMovie(@RequestBody @Valid RequestMovie movieDTO) {
        Movie savedMovie = movieService.addMovie(movieDTO.toEntity());
        ResponseMovie response = new ResponseMovie();
        response.setId(savedMovie.getId());
        response.setTitle(savedMovie.getTitle());
        response.setGenre(savedMovie.getGenre());
        response.setReleaseYear(savedMovie.getReleaseYear());
        response.setRating(savedMovie.getRating());
        return response;
    }

    @GetMapping
    public List<ResponseMovie> getAllMovies() {
        return movieService.getAllMovies().stream().map(movie -> {
            ResponseMovie dto = new ResponseMovie();
            dto.setId(movie.getId());
            dto.setTitle(movie.getTitle());
            dto.setGenre(movie.getGenre());
            dto.setReleaseYear(movie.getReleaseYear());
            dto.setRating(movie.getRating());
            return dto;
        }).toList();
    }

    @GetMapping(params = "genre")
    public List<ResponseMovie> getMoviesByGenre(@RequestParam String genre) {
        return movieService.getMoviesByGenre(genre).stream().map(movie -> {
            ResponseMovie dto = new ResponseMovie();
            dto.setId(movie.getId());
            dto.setTitle(movie.getTitle());
            dto.setGenre(movie.getGenre());
            dto.setReleaseYear(movie.getReleaseYear());
            dto.setRating(movie.getRating());
            return dto;
        }).toList();
    }

    @GetMapping(params = "releaseYear")
    public List<ResponseMovie> getMoviesByReleaseYear(@RequestParam Integer releaseYear) {
        return movieService.getMoviesByReleaseYear(releaseYear).stream().map(movie -> {
            ResponseMovie dto = new ResponseMovie();
            dto.setId(movie.getId());
            dto.setTitle(movie.getTitle());
            dto.setGenre(movie.getGenre());
            dto.setReleaseYear(movie.getReleaseYear());
            dto.setRating(movie.getRating());
            return dto;
        }).toList();
    }

    @GetMapping(params = "rating")
    public List<ResponseMovie> getMoviesByRating(@RequestParam Integer rating) {
        return movieService.getMoviesByRating(rating).stream().map(movie -> {
            ResponseMovie dto = new ResponseMovie();
            dto.setId(movie.getId());
            dto.setTitle(movie.getTitle());
            dto.setGenre(movie.getGenre());
            dto.setReleaseYear(movie.getReleaseYear());
            dto.setRating(movie.getRating());
            return dto;
        }).toList();
    }

    @GetMapping(params = "title")
    public List<ResponseMovie> getMoviesByTitle(@RequestParam String title) {
        return movieService.getMoviesByTitleContaining(title).stream().map(movie -> {
            ResponseMovie dto = new ResponseMovie();
            dto.setId(movie.getId());
            dto.setTitle(movie.getTitle());
            dto.setGenre(movie.getGenre());
            dto.setReleaseYear(movie.getReleaseYear());
            dto.setRating(movie.getRating());
            return dto;
        }).toList();
    }

    @GetMapping(params = {"genre", "releaseYear"})
    public List<ResponseMovie> getMoviesByGenreAndReleaseYear(@RequestParam String genre, @RequestParam Integer releaseYear) {
        return movieService.getMoviesByGenreAndReleaseYear(genre, releaseYear).stream().map(movie -> {
            ResponseMovie dto = new ResponseMovie();
            dto.setId(movie.getId());
            dto.setTitle(movie.getTitle());
            dto.setGenre(movie.getGenre());
            dto.setReleaseYear(movie.getReleaseYear());
            dto.setRating(movie.getRating());
            return dto;
        }).toList();
    }

}
