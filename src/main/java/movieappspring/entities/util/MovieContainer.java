package movieappspring.entities.util;

import movieappspring.entities.Movie;
import movieappspring.entities.Review;

import java.util.List;
import java.util.Map;

/**
 * Class stores all movie-related data
 */
public class MovieContainer {

    private Movie movie;
    private List<Review> reviews;
    private Map<Long, Object> users;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Map<Long, Object> getUsers() {
        return users;
    }

    public void setUsers(Map<Long, Object> users) {
        this.users = users;
    }
}
