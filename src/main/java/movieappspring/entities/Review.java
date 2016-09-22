package movieappspring.entities;

import movieappspring.validation.PostReviewValidation;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.sql.Date;

/**
 * Class representing <code>Review</code>entity.
 */
@Entity
public class Review {

    /**
     * Review id from database
     */
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    /**
     * id of user - author of review
     */
    private Long userId;

    /**
     * id of the movie which review is written to
     */
    @NotNull(groups = PostReviewValidation.class)
    @Min(value = 1, groups = PostReviewValidation.class)
    private Long movieId;

    /**
     * Date when review has been posted
     */
    private Date postDate;

    /**
     * Title of review
     */
    @NotNull(groups = PostReviewValidation.class)
    @Size(min = 3, max = 100, message = "{review.title.size}", groups = PostReviewValidation.class)
    @Pattern(regexp = "[a-zA-zа-яА-я0-9]+([ '-][a-zA-Zа-яА-Я0-9]+)*", message = "{review.title.pattern}",
            groups = PostReviewValidation.class)
    @Column(name = "reviewtitle")
    private String title;

    /**
     * Review text written by user
     */
    @NotNull(groups = PostReviewValidation.class)
    @Size(min = 5, max = 2000, message = "{review.reviewText.size}", groups = PostReviewValidation.class)
    @Pattern(regexp = "[a-zA-zа-яА-я0-9@()!.,+&=?:\\\\-\\\\\"']+([ '-][a-zA-Zа-яА-Я0-9@()!.,+&=?:\\\\\"'\\\\-]+)*",
            message = "{review.reviewText.pattern}", groups = PostReviewValidation.class)
    private String reviewText;

    /**
     * Rating given by user to movie
     */
    @NotNull(groups = PostReviewValidation.class)
    @Min(value = 1, message = "{review.rating.min}", groups = PostReviewValidation.class)
    @Max(value = 10, message = "{review.rating.max}", groups = PostReviewValidation.class)
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
