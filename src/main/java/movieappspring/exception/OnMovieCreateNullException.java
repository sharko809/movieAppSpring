package movieappspring.exception;

import movieappspring.entities.Movie;

/**
 * This exception is thrown when SQL, Hibernate or Database exception occur during writing (adding movie) to database
 */
public class OnMovieCreateNullException extends Exception {

    private Movie movie;

    public OnMovieCreateNullException(String message, Movie movie) {
        super(message);
        this.movie = movie;
    }

    public String movieDetails() {
        return "Movie{" +
                "id=" + movie.getId() +
                ", movieName='" + movie.getMovieName() + '\'' +
                ", director='" + movie.getDirector() + '\'' +
                ", releaseDate=" + movie.getReleaseDate() +
                ", posterURL='" + movie.getPosterURL() + '\'' +
                ", trailerURL='" + movie.getTrailerURL() + '\'' +
                ", rating=" + movie.getRating() +
                ", description='" + movie.getDescription() + '\'' +
                '}';
    }

}
