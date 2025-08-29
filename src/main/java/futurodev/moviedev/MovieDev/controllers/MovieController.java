package futurodev.moviedev.MovieDev.controllers;

import futurodev.moviedev.MovieDev.dtos.RequestMovie;
import futurodev.moviedev.MovieDev.dtos.ResponseMovie;
import futurodev.moviedev.MovieDev.models.Movie;
import futurodev.moviedev.MovieDev.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(summary = "Excluir todos os filmes cadastrados", description = "Exclusão em massa de todos os filmes cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Todos os filmes foram deletados com sucesso!",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro Interno no Servidor",
                    content = {
                            @Content(mediaType = "Application/json")
                    })
    })
    @DeleteMapping
    public ResponseEntity<?> deleteAllMovies() {
        log.trace("deleteAllMovies - start");
        String message = movieService.deleteAllMovies();
        log.trace("deleteAllMovies - end");
        return ResponseEntity.ok().body(Map.of("message", message));
    }

    @Operation(summary = "Excluir um filme pelo ID", description = "Exclusão de um filme específico utilizando seu ID.")
    @Parameter(name = "id", description = "ID do filme a ser excluído", required = true)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Filme com ID {id} deletado com sucesso!",
                    content = {
                            @Content(mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Filme com ID {id} não encontrado.",
                    content = {
                            @Content(mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro Interno no Servidor",
                    content = {
                            @Content(mediaType = "application/json")
                    })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovieById(@PathVariable Long id) {
        log.trace("deleteMovieById - start - id: {}", id);
        String message = movieService.deleteMovieById(id);
        return ResponseEntity.ok().body(Map.of("message", message));
    }

    @Operation(summary = "Contar o total de filmes cadastrados", description = "Retorna a contagem total de filmes presentes no sistema.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contagem total de filmes retornada com sucesso.",
                    content = {
                            @Content(mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro Interno no Servidor",
                    content = {
                            @Content(mediaType = "application/json")
                    })
    })
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countMovies() {
        log.trace("countMovies - start");
        long count = movieService.countMovies();
        log.trace("countMovies - end - count: {}", count);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @Operation(summary = "Atualizar um filme pelo ID", description = "Atualiza os detalhes de um filme específico utilizando seu ID.")
    @Parameter(name = "id", description = "ID do filme a ser atualizado", required = true)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Filme com ID {id} atualizado com sucesso!",
                    content = {
                            @Content(mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Filme com ID {id} não encontrado.",
                    content = {
                            @Content(mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida.",
                    content = {
                            @Content(mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro Interno no Servidor",
                    content = {
                            @Content(mediaType = "application/json")
                    })
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateMovie(@PathVariable Long id,
                                                           @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                     description = "Detalhes atualizados do filme",
                                                                     required = true,
                                                                     content = @Content(
                                                                            schema = @Schema(implementation = RequestMovie.class),
                                                                            examples = @ExampleObject(value = """
                                                                                      {
                                                                                        "title": "Inception",
                                                                                        "genre": "Sci-Fi",
                                                                                        "releaseYear": 2010,
                                                                                        "rating": 9
                                                                                      }
                                                                                      """)
                                                                     )
                                                           )
                                                           @RequestBody @Valid RequestMovie updatedMovieDTO) {
        log.trace("updateMovie - start - id: {}, updatedMovieDTO: {}", id, updatedMovieDTO);
        String message = movieService.updateMovie(id, updatedMovieDTO.toEntity());
        log.trace("updateMovie - end - message: {}", message);
        return ResponseEntity.ok(Map.of("message", message));
    }

    @Operation(summary = "Adicionar um novo filme", description = "Adiciona um novo filme ao sistema.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Filme adicionado com sucesso!",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ResponseMovie.class),
                                            examples = @ExampleObject(value = """
                                                      {
                                                        "id": 1,
                                                        "title": "Inception",
                                                        "genre": "Sci-Fi",
                                                        "releaseYear": 2010,
                                                        "rating": 9
                                                      }
                                                      """)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida.",
                            content = {
                                    @Content(mediaType = "application/json")
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro Interno no Servidor",
                            content = {
                                    @Content(mediaType = "application/json")
                            }
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Filme já existe no banco de dados.",
                            content = {
                                    @Content(mediaType = "application/json")
                            }
                    )
            }

    )
    @PostMapping
    public ResponseMovie addMovie(@RequestBody @Valid RequestMovie movieDTO) {
        log.trace("addMovie - start - movieDTO: {}", movieDTO);
        Movie savedMovie = movieService.addMovie(movieDTO.toEntity());
        log.trace("addMovie - end - savedMovie: {}", savedMovie);
        ResponseMovie response = new ResponseMovie();
        response.setId(savedMovie.getId());
        response.setTitle(savedMovie.getTitle());
        response.setGenre(savedMovie.getGenre());
        response.setReleaseYear(savedMovie.getReleaseYear());
        response.setRating(savedMovie.getRating());
        log.trace("addMovie - response: {}", response);
        return response;
    }

    @Operation(summary = "Listar todos os filmes", description = "Retorna uma lista de todos os filmes cadastrados no sistema.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de filmes retornada com sucesso.",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ResponseMovie.class),
                                            examples = @ExampleObject(value = """
                                                      [
                                                        {
                                                          "id": 1,
                                                          "title": "Inception",
                                                          "genre": "Sci-Fi",
                                                          "releaseYear": 2010,
                                                          "rating": 9
                                                        },
                                                        {
                                                          "id": 2,
                                                          "title": "The Dark Knight",
                                                          "genre": "Action",
                                                          "releaseYear": 2008,
                                                          "rating": 9
                                                        }
                                                      ]
                                                      """)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro Interno no Servidor",
                            content = {
                                    @Content(mediaType = "application/json")
                            }
                    )
            }
    )
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
    public List<ResponseMovie> getMoviesByGenre(
            @Parameter(name = "genre", description = "Gênero dos filmes a serem filtrados", required = false)
            @RequestParam(required = false) String genre) {
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
    public List<ResponseMovie> getMoviesByReleaseYear(
            @Parameter(name = "releaseYear", description = "Ano de lançamento dos filmes a serem filtrados", required = false)
            @RequestParam(required = false) Integer releaseYear) {
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
    public List<ResponseMovie> getMoviesByRating(
            @Parameter(name = "rating", description = "Classificação dos filmes a serem filtrados", required = false)
            @RequestParam(required = false) Integer rating) {
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
    public List<ResponseMovie> getMoviesByTitle(
            @Parameter(name = "title", description = "Título (ou parte dele) dos filmes a serem filtrados")
            @RequestParam(required = false) String title) {
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
    public List<ResponseMovie> getMoviesByGenreAndReleaseYear(
            @Parameter(name = "genre", description = "Gênero dos filmes a serem filtrados", required = false)
            @RequestParam(required = false) String genre,
            @Parameter(name = "releaseYear", description = "Ano de lançamento dos filmes a serem filtrados", required = false)
            @RequestParam(required = false) Integer releaseYear) {
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
