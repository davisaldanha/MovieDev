package futurodev.moviedev.MovieDev.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Year;

@Entity
@Table(name ="tb_movie")
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 50)
    private String genre;

    @Column(nullable = false, length = 4)
    private Integer releaseYear;

    @Column(nullable = false)
    private Integer rating;
}
