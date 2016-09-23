package movieappspring.entities;


import movieappspring.entities.dto.MovieTransferObject;
import movieappspring.validation.annotation.ValidMovieTransferObjectURL;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.sql.Date;

/**
 * Class representing <code>Movie</code> entity.
 */
@Entity
public class Movie {

    /**
     * Movie id from database
     */
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    /**
     * Movie title
     */
    @NotNull
    @Size(min = 1, max = 30, message = "{movie.title.size}")
    @Pattern(regexp = "[a-zA-zа-яА-яё0-9(){},]+([ '-][a-zA-Zа-яА-Яё0-9(){},]+)*", message = "{movie.title.pattern}")
    private String movieName;

    /**
     * Name of movie's director
     */
    @Size(min = 1, max = 30, message = "{movie.director.size}")
    @Pattern(regexp = "[a-zA-zа-яА-яё(){},]+([ '-][a-zA-Zа-яА-Яё(){},]+)*", message = "{movie.director.pattern}")
    private String director;

    /**
     * Movie release date
     */
    private Date releaseDate;

    /**
     * URL leading to poster for the movie
     */
    @ValidMovieTransferObjectURL(min = 7, max = 255)
    private String posterURL;

    /**
     * URL leading to embed trailer for the movie
     */
    @ValidMovieTransferObjectURL(min = 7, max = 255)
    private String trailerURL;

    /**
     * Movie rating calculated based on users reviews
     */
    @Min(value = 0)
    @Max(value = 10)
    private Double rating;

    /**
     * Some description for the movie
     */
    @NotNull
    @Size(min = 5, max = 2000, message = "{movie.description.size}")
    @Pattern(regexp = "[a-zA-zа-яА-яё0-9@()!.,+&=?:\\\\-\\\\\"']+([ '-][a-zA-Zа-яА-Яё0-9@()!.,+&=?:\\\\\"'\\\\-]+)*")
    private String description;

    public Movie() {
    }

    public Movie(MovieTransferObject movieTransferObject) {
        this.movieName = movieTransferObject.getMovieName();
        this.director = movieTransferObject.getDirector();
        this.releaseDate = movieTransferObject.getReleaseDate();
        this.posterURL = movieTransferObject.getPosterURL();
        this.trailerURL = movieTransferObject.getTrailerURL();
        this.rating = movieTransferObject.getRating();
        this.description = movieTransferObject.getDescription();
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String name) {
        this.movieName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}