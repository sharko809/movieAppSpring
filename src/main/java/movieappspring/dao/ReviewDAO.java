package movieappspring.dao;

import movieappspring.entities.Review;

import java.util.List;

/**
 * Interface declaring witch methods should be used in each particular Review DAO implementation
 */
public interface ReviewDAO {

    Long create(Review review);

    Review get(Long reviewID);

    void update(Review review);

    List<Review> getReviewsByMovieId(Long movieID);

    void delete(Review review);

}
