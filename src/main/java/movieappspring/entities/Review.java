package movieappspring.entities;

import java.sql.Date;

/**
 * Created by dsharko on 7/28/2016.
 */
public class Review {

    /**
     * Review id from database
     */
    private Long id;

    /**
     * id of user - author of review
     */
    private Long userId;

    /**
     * id of the movie which review is written to
     */
    private Long movieId;

    /**
     * Date when review has been posted
     */
    private Date postDate;

    /**
     * Title of review
     */
    private String title;

    /**
     * Review text written by user
     */
    private String reviewText;

    /**
     * Rating given by user to movie
     */
    private Integer rating;

    public Review() {
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
