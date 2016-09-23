package movieappspring.entities;

import movieappspring.entities.dto.ReviewTransferObject;
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
     * Date when review has been posted
     */
    private Date postDate;

    /**
     * Title of review
     */
    @NotNull
    @Size(min = 3, max = 100, message = "{review.title.size}")
    @Pattern(regexp = "[a-zA-zа-яА-яё0-9]+([ '-][a-zA-Zа-яА-Яё0-9]+)*", message = "{review.title.pattern}")
    @Column(name = "reviewtitle")
    private String title;

    /**
     * id of the movie which review is written to
     */
    @NotNull
    @Min(value = 1)
    private Long movieId;

    /**
     * Review text written by user
     */
    @NotNull
    @Size(min = 5, max = 2000, message = "{review.reviewText.size}")
    @Pattern(regexp = "[a-zA-zа-яА-я0-9ё@()!.,+&=?:\\\\-\\\\\"']+([ '-][a-zA-Zа-яА-Я0-9ё@()!.,+&=?:\\\\\"'\\\\-]+)*",
            message = "{review.reviewText.pattern}")
    private String reviewText;

    /**
     * Rating given by user to movie
     */
    @NotNull
    @Min(value = 1, message = "{review.rating.min}")
    @Max(value = 10, message = "{review.rating.max}")
    private Integer rating;

    public Review() {
    }

    public Review(ReviewTransferObject reviewTransferObject) {
        this.title=reviewTransferObject.getTitle();
        this.reviewText=reviewTransferObject.getText();
        this.rating=reviewTransferObject.getRating();
        this.postDate=reviewTransferObject.getPostDate();
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