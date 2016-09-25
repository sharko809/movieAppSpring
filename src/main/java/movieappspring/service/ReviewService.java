package movieappspring.service;

import movieappspring.dao.ReviewDAO;
import movieappspring.entities.Review;
import movieappspring.exception.OnGetNullException;
import movieappspring.exception.OnReviewCreateNullException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
     * @param review review to be added to database
     * @return ID of created review
     * @throws OnReviewCreateNullException can be thrown when some significant issue had happened. Such as SQLException,
     * Hibernate Exception or database error.
     */
    public Long createReview(Review review) throws OnReviewCreateNullException {
        Long reviewId = reviewDAO.create(review);
        if (reviewId == null) {
            LOGGER.error("Unable to create review. DAO method returned null.");
            throw new OnReviewCreateNullException("Unable to create review.", review);
        }
        return reviewId;
    }

    /**
     * Get review if exists by its ID
     *
     * @param reviewId ID of review to be found
     * @return <code>Review</code> object if review with given ID exists.
     * @see Review
     * @throws OnGetNullException can be thrown when some significant issue had happened. Such as SQLException,
     * Hibernate Exception or database error.
     */
    public Review getReview(Long reviewId) throws OnGetNullException {
        Review review = reviewDAO.get(reviewId);
        if (review == null) {
            LOGGER.error("Unable to get review with id " + reviewId + ". DAO method returned null");
            throw new OnGetNullException("Unable to get review with id " + reviewId + ".");
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
     * @param review review to be removed from database
     */
    public void deleteReview(Review review) {
        reviewDAO.delete(review);
    }

    /**
     * Get all reviews for specified movie
     *
     * @param movieId ID of movie which reviews refer to
     * @return List of <code>Review</code>objects if found any. Otherwise returns empty List.
     * @see Review
     * @throws OnGetNullException can be thrown when some significant issue had happened. Such as SQLExcetion,
     * Hibernate Exception or database error.
     */
    public List<Review> getReviewsByMovieId(Long movieId) throws OnGetNullException {
        List<Review> reviews = reviewDAO.getReviewsByMovieId(movieId);
        if (reviews == null) {
            LOGGER.error("Unable to get reviews for movie with id " + movieId + ". DAO method returned null");
            throw new OnGetNullException("Unable to get reviews for movie with id " + movieId + ".");
        }
        return reviews;
    }

}