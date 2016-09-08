package movieappspring.entities;


import java.sql.Date;

/**
 * Class representing <code>Movie</code> entity.
 */
public class Movie {

    /**
     * Movie id from database
     */
    private Long id;

    /**
     * Movie title
     */
    private String movieName;

    /**
     * Name of movie's director
     */
    private String director;

    /**
     * Movie release date
     */
    private Date releaseDate;

    /**
     * URL leading to poster for the movie
     */
    private String posterURL;

    /**
     * URL leading to embed trailer for the movie
     */
    private String trailerURL;

    /**
     * Movie rating calculated based on users reviews
     */
    private Double rating;

    /**
     * Some description for the movie
     */
    private String description;

    public Movie() {
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
