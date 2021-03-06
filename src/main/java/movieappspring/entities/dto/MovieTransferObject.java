package movieappspring.entities.dto;

import movieappspring.entities.Movie;
import movieappspring.validation.annotation.ValidDate;
import movieappspring.validation.annotation.ValidMovieTransferObjectURL;

import javax.validation.constraints.*;
import java.sql.Date;

/**
 * Helper class used as DTO for movies.
 */
public class MovieTransferObject {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100, message = "{movie.title.size}")
    @Pattern(regexp = "[a-zA-zа-яА-яё0-9(){},.:'ßé!?üôöóâä-åøí&Åñ]+([ '-][a-zA-Zа-яА-Яё0-9(){},.:'ßé!?üôöóâä-åøí&Åñ]+)*",
            message = "{movie.title.pattern}")
    private String movieName;

    @Size(min = 1, max = 40, message = "{movie.director.size}")
    @Pattern(regexp = "[a-zA-zа-яА-яёöá(){},.:'ßé!?üôóâäåøíÅñ]+([ '-][a-zA-Zа-яА-Яöáё(){},.:'ßé!?üôóâäåøíÅñ]+)*",
            message = "{movie.director.pattern}")
    private String director;

    @ValidDate
    private Date releaseDate;

    @ValidMovieTransferObjectURL(min = 7, max = 255)
    private String posterURL;

    @ValidMovieTransferObjectURL(min = 7, max = 255)
    private String trailerURL;

    @Min(value = 0)
    @Max(value = 10)
    private Double rating;

    @NotNull
    @Size(min = 5, max = 2000, message = "{movie.description.size}")
    @Pattern(regexp = "[a-zA-zа-яА-яё0-9@()!.,+&=?:\\\\-\\\\\"']+([ '-][a-zA-Zа-яА-Яё0-9@()!.,+&=?:\\\\\"'\\\\-]+)*")
    private String description;

    public MovieTransferObject() {
    }

    public MovieTransferObject(Movie movie) {
        this.id = movie.getId();
        this.movieName = movie.getMovieName();
        this.director = movie.getDirector();
        this.releaseDate = movie.getReleaseDate();
        this.posterURL = movie.getPosterURL();
        this.trailerURL = movie.getTrailerURL();
        this.rating = movie.getRating();
        this.description = movie.getDescription();
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

    public void setMovieName(String movieName) {
        this.movieName = movieName;
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

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}