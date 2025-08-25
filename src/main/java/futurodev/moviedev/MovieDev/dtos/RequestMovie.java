package futurodev.moviedev.MovieDev.dtos;

import futurodev.moviedev.MovieDev.models.Movie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class RequestMovie {
    @NotBlank(message = "O título é obrigatório")
    @Size(min = 2, max = 100, message = "O título deve ter entre 2 e 100 caracteres")
    private String title;
    @NotBlank(message = "O gênero é obrigatório")
    @Size(min = 2, max = 50, message = "O gênero deve ter entre 2 e 50 caracteres")
    private String genre;
    @NotNull(message = "O ano de lançamento é obrigatório")
    @Range(min = 1800, max = 2100, message = "O ano de lançamento deve estar entre 1800 e 2100")
    private Integer releaseYear;
    @NotNull(message = "A avaliação é obrigatória")
    @Range(min = 0, max = 10, message = "A avaliação deve estar entre 0 e 10")
    private Integer rating;


    public Movie toEntity() {
        Movie movie = new Movie();
        movie.setTitle(this.title);
        movie.setGenre(this.genre);
        movie.setReleaseYear(this.releaseYear);
        movie.setRating(this.rating);
        return movie;
    }
}
