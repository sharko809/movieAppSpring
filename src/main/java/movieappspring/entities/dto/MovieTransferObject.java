package movieappspring.entities.dto;

import movieappspring.validation.annotation.ValidMovieTransferObject;

import javax.validation.constraints.*;
import java.sql.Date;

/**
 * Helper class used as DTO for movies.
 */
@ValidMovieTransferObject
public class MovieTransferObject {

    @NotNull
    @Size(min = 1, max = 30, message = "{movie.title.size}")
    @Pattern(regexp = "[a-zA-zа-яА-я0-9]+([ '-][a-zA-Zа-яА-Я0-9]+)*", message = "{movie.title.pattern}")
    private String movieName;

    @Size(min = 1, max = 30, message = "{movie.director.size}")
    @Pattern(regexp = "[a-zA-zа-яА-я]+([ '-][a-zA-Zа-яА-Я]+)*", message = "{movie.director.pattern}")
    private String director;

    private Date releaseDate;

//    @Size(min = 7, max = 255, message = "{movie.poster.size}")
//    @Pattern(regexp = "((?:(http|https|Http|Https|rtsp|Rtsp|ftp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&amp;\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_" +
//            "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&amp;\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.)+" +
//            "(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])" +
//            "|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnrwyz]" +
//            "|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eouw]" +
//            "|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw]))|(?:(?:25[0-5]|2[0-4]" +
//            "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]" +
//            "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?:\\:\\d{1,5})?)" +
//            "(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&amp;\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$)",
//            message = "{movie.poster.pattern}")
//    @ValidMovieTransferObject
    private String posterURL;

    @Size(min = 7, max = 255, message = "{movie.trailer.size}")
    @Pattern(regexp = "((?:(http|https|Http|Https|rtsp|Rtsp|ftp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&amp;\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_" +
            "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&amp;\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.)+" +
            "(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])" +
            "|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnrwyz]" +
            "|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eouw]" +
            "|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw]))|(?:(?:25[0-5]|2[0-4]" +
            "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]" +
            "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?:\\:\\d{1,5})?)" +
            "(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&amp;\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$)",
            message = "{movie.trailer.pattern}")
    private String trailerURL;

//    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Double rating;

    @NotNull
    @Size(min = 5, max = 2000, message = "{movie.description.size}")
    @Pattern(regexp = "[a-zA-zа-яА-я0-9@()!.,+&=?:\\\\-\\\\\"']+([ '-][a-zA-Zа-яА-Я0-9@()!.,+&=?:\\\\\"'\\\\-]+)*")
    private String description;

    public MovieTransferObject() {

    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}