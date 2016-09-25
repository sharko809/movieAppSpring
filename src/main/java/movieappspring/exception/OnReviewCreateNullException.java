package movieappspring.exception;

import movieappspring.entities.Review;

/**
 * This exception is thrown when SQL, Hibernate or Database exception occur during writing (posting review) to database
 */
public class OnReviewCreateNullException extends Exception {

    private Review review;

    public OnReviewCreateNullException(String message, Review review) {
        super(message);
        this.review = review;
    }

    public String reviewDetails() {
        return "Review{" +
                "id=" + review.getId() +
                ", userId=" + review.getUserId() +
                ", postDate=" + review.getPostDate() +
                ", title='" + review.getTitle() + '\'' +
                ", movieId=" + review.getMovieId() +
                ", reviewText='" + review.getReviewText() + '\'' +
                ", rating=" + review.getRating() +
                '}';
    }
}
