package movieappspring.dao;

import movieappspring.entities.Review;

import java.sql.Date;
import java.util.List;

/**
 * Interface declaring witch methods should be used in each particular Review DAO implementation
 */
public interface ReviewDAO {

    Long create(Long userID, Long movieID, Date postDate, String reviewTitle, Integer rating, String reviewText);

    Review get(Long reviewID);

    void update(Review review);

    List<Review> getReviewsByMovieId(Long movieID);

    boolean delete(Long reviewID);

}
