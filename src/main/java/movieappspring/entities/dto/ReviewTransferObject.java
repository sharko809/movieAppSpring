package movieappspring.entities.dto;

import movieappspring.validation.PostReviewValidation;

import javax.validation.constraints.*;

/**
 * Helper class used as DTO for reviews.
 */
public class ReviewTransferObject {

    @NotNull(groups = PostReviewValidation.class)
    @Size(min = 3, max = 100, message = "{review.title.size}", groups = PostReviewValidation.class)
    @Pattern(regexp = "[a-zA-zа-яА-я0-9]+([ '-][a-zA-Zа-яА-Я0-9]+)*", message = "{review.title.pattern}",
            groups = PostReviewValidation.class)
    private String title;

    @NotNull(groups = PostReviewValidation.class)
    @Min(value = 1, message = "{review.rating.min}", groups = PostReviewValidation.class)
    @Max(value = 10, message = "{review.rating.max}", groups = PostReviewValidation.class)
    private Integer rating;

    @NotNull(groups = PostReviewValidation.class)
    @Size(min = 5, max = 2000, message = "{review.reviewText.size}", groups = PostReviewValidation.class)
    @Pattern(regexp = "[a-zA-zа-яА-я0-9@()!.,+&=?:\\-\"'\\[\\]{\\}]+([ '-][a-zA-Zа-яА-Я0-9@()!.,+&=?:\\\\\"'\\-\\[\\]{\\}]+)*",
            message = "{review.reviewText.pattern}", groups = PostReviewValidation.class)
    private String text;

    public ReviewTransferObject() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}