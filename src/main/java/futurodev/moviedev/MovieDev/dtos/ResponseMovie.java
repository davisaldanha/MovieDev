package futurodev.moviedev.MovieDev.dtos;

import lombok.Data;

@Data
public class ResponseMovie {

    private Long id;
    private String title;
    private String genre;
    private Integer releaseYear;
    private Integer rating;

}
