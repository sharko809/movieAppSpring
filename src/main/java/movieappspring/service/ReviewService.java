package movieappspring.service;

import movieappspring.dao.ReviewDAO;
import movieappspring.entities.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.List;

/**
 * Class containing all methods to interact with reviews
 */
public class ReviewService {

    private static final Logger LOGGER = LogManager.getLogger();
    private ReviewDAO reviewDAO;

    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    /**
     * Creates new review for the movie
     *
     * @param userId      ID of user who submitted review
     * @param movieId     ID of movie to which this review is submitted
     * @param postDate    date when user submitted review
     * @param reviewTitle title of users review
     * @param rating      rating given by user to the movie
     * @param reviewText  text of submitted review
     * @return ID of created review
     */
    public Long createReview(Long userId, Long movieId, Date postDate, String reviewTitle,
                             Integer rating, String reviewText) {
        Long reviewId = reviewDAO.create(userId, movieId, postDate, reviewTitle, rating, reviewText);
        if (reviewId == null || reviewId == 0) {
            LOGGER.error("Unable to create review. DAO method returned " + reviewId);
            // TODO create exception
        }
        return reviewId;
    }

    /**
     * Get review if exists by its ID
     *
     * @param reviewId ID of review to be found
     * @return <code>Review</code> object if review with given ID exists.
     * @see Review
     */
    public Review getReview(Long reviewId) {
        Review review = reviewDAO.get(reviewId);
        if (review == null) {
            LOGGER.error("Unable to get review with id " + reviewId + ". DAO method returned null");
            // TODO create exception
        }
        return review;
    }

    /**
     * Updates review data in database
     *
     * @param review <code>Review</code> object to update
     * @see Review
     */
    public void updateReview(Review review) {
        reviewDAO.update(review);
    }

    /**
     * Deletes review
     *
     * @param reviewId ID of review to be removed from database
     * @return <b>true</b> if review has been successfully deleted. Otherwise returns <b>false</b>
     */
    public boolean deleteReview(Long reviewId) {
        return reviewDAO.delete(reviewId);
    }

    /**
     * Get all reviews for specified movie
     *
     * @param movieId ID of movie which reviews refer to
     * @return List of <code>Review</code>objects if found any. Otherwise returns empty List.
     * @see Review
     */
    public List<Review> getReviewsByMovieId(Long movieId) {
        List<Review> reviews = reviewDAO.getReviewsByMovieId(movieId);
        if (reviews == null) {
            LOGGER.error("Unable to get reviews for movie with id " + movieId + ". DAO method returned null");
            // TODO create exception
        }
        return reviews;
    }

}