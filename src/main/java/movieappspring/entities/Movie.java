package movieappspring.entities;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Class representing <code>Movie</code> entity.
 */
public class Movie {

    /**
     * Movie id from database
     */
    private Long id;

    /**
     * Movie title
     */
    @NotNull
    @Size(min = 1, max = 30, message = "{movie.title.size}")
    @Pattern(regexp = "[a-zA-zа-яА-я0-9]+([ '-][a-zA-Zа-яА-Я0-9]+)*", message = "{movie.title.pattern}")
    private String movieName;

    /**
     * Name of movie's director
     */
    @Size(min = 1, max = 30, message = "{movie.director.size}")
    @Pattern(regexp = "[a-zA-zа-яА-я]+([ '-][a-zA-Zа-яА-Я]+)*", message = "{movie.director.pattern}")
    private String director;

    /**
     * Movie release date
     */
    private Date releaseDate;

    /**
     * URL leading to poster for the movie
     */
    @Size(min = 7, max = 255, message = "{movie.poster.size}")
    @Pattern(regexp = "((?:(http|https|Http|Https|rtsp|Rtsp|ftp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&amp;\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\" +\n" +
            "                    \"\\\\.\\\\+\\\\!\\\\*\\\\'\\\\(\\\\)\\\\,\\\\;\\\\?\\\\&amp;\\\\=]|(?:\\\\%[a-fA-F0-9]{2})){1,25})?\\\\@)?)?((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\\\-]{0,64}\\\\.)+\" +\n" +
            "                    \"(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])\" +\n" +
            "                    \"|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnrwyz]\" +\n" +
            "                    \"|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eouw]\" +\n" +
            "                    \"|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw]))|(?:(?:25[0-5]|2[0-4]\" +\n" +
            "                    \"[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\\\.(?:25[0-5]|2[0-4][0-9]|[0-1]\" +\n" +
            "                    \"[0-9]{2}|[1-9][0-9]|[1-9]|0)\\\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?:\\\\:\\\\d{1,5})?)\" +\n" +
            "                    \"(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&amp;\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$)",
    message = "{movie.poster.pattern}")
    private String posterURL;

    /**
     * URL leading to embed trailer for the movie
     */
    @Size(min = 7, max = 255, message = "{movie.trailer.size}")
    @Pattern(regexp = "((?:(http|https|Http|Https|rtsp|Rtsp|ftp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&amp;\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\" +\n" +
            "\"\\\\.\\\\+\\\\!\\\\*\\\\'\\\\(\\\\)\\\\,\\\\;\\\\?\\\\&amp;\\\\=]|(?:\\\\%[a-fA-F0-9]{2})){1,25})?\\\\@)?)?((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\\\-]{0,64}\\\\.)+\" +\n" +
            "\"(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])\" +\n" +
            "\"|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnrwyz]\" +\n" +
            "\"|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eouw]\" +\n" +
            "\"|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw]))|(?:(?:25[0-5]|2[0-4]\" +\n" +
            "\"[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\\\.(?:25[0-5]|2[0-4][0-9]|[0-1]\" +\n" +
            "\"[0-9]{2}|[1-9][0-9]|[1-9]|0)\\\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?:\\\\:\\\\d{1,5})?)\" +\n" +
            "\"(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&amp;\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$)",
    message = "{movie.trailer.pattern}")
    private String trailerURL;

    /**
     * Movie rating calculated based on users reviews
     */
    private Double rating;

    /**
     * Some description for the movie
     */
    @NotNull
    @Size(min = 5, max = 2000, message = "{movie.description.size}")
    @Pattern(regexp = "[a-zA-zа-яА-я0-9@()!.,+&=?:\\\\-\\\\\"']+([ '-][a-zA-Zа-яА-Я0-9@()!.,+&=?:\\\\\"'\\\\-]+)*")
    private String description;

    public Movie() {
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String name) {
        this.movieName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
