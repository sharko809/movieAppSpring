package movieappspring.dao;

import movieappspring.entities.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that accesses the database and performs any activity connected with database.
 * <p>
 * This particular class deals with review data in database.
 */
public class ReviewDAO {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Query for adding new record with review data to database
     */
    private static final String SQL_CREATE_REVIEW = "INSERT INTO REVIEW " +
            "(userid, movieid, postdate, reviewtitle, rating, reviewtext) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Query for selecting review with given ID from database
     */
    private static final String SQL_GET_REVIEW = "SELECT * FROM REVIEW WHERE ID = ?";

    /**
     * Query for removing review with given ID from database
     */
    private static final String SQL_DELETE_REVIEW = "DELETE FROM REVIEW WHERE ID = ?";

    /**
     * Query for updating review record in database
     */
    private static final String SQL_UPDATE_REVIEW = "UPDATE REVIEW SET " +
            "userID = ?, " +
            "movieID = ?, " +
            "postdate = ?, " +
            "reviewtitle = ?," +
            "rating = ?, " +
            "reviewtext = ? WHERE ID = ?";

    /**
     * Query for retrieving review record for specified movie from database
     */
    private static final String SQL_GET_REVIEWS_BY_MOVIE_ID = "SELECT * FROM REVIEW WHERE movieid = ?";

    private ConnectionManager connectionManager;

    @Autowired
    public ReviewDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Helper method to parse result set
     *
     * @param resultSet result set to parse
     * @return Review object with filled fields
     */
    private Review parseReviewResultSet(ResultSet resultSet) {
        Review review = new Review();
        try {
            review.setId(resultSet.getLong("id"));
            review.setUserId(resultSet.getLong("userid"));
            review.setMovieId(resultSet.getLong("movieid"));
            review.setPostDate(resultSet.getDate("postdate"));
            review.setTitle(resultSet.getString("reviewtitle"));
            review.setRating(resultSet.getInt("rating"));
            review.setReviewText(resultSet.getString("reviewtext"));
        } catch (SQLException e) {
            LOGGER.error("Error parsing reviews result set.", e);
            return null;
        }
        return review;
    }

    /**
     * Creates a record for new user in database
     *
     * @param userID      ID of user who submitted review
     * @param movieID     ID of movie to which this review is submitted
     * @param postDate    date when user submitted review
     * @param reviewTitle title of users review
     * @param rating      rating given by user to the movie
     * @param reviewText  text of submitted review
     * @return ID of created review. If review to some reasons hasn't been created - returns 0.
     */
    public Long create(Long userID, Long movieID, Date postDate, String reviewTitle, Integer rating, String reviewText) {
        Long reviewID = 0L;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE_REVIEW, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, userID);
            statement.setLong(2, movieID);
            statement.setDate(3, postDate);
            statement.setString(4, reviewTitle);
            statement.setInt(5, rating);
            statement.setString(6, reviewText);
            statement.executeUpdate();
            ResultSet resultSet = null;
            try {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    reviewID = resultSet.getLong(1);
                }
            } catch (SQLException e) {
                throw new SQLException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error creating review record in database.", e);
            return null;
        }
        return reviewID;
    }

    /**
     * Searches for review with specified ID in database
     *
     * @param reviewID ID of review to be found
     * @return <code>Review</code> object if review with given ID exists.
     */
    public Review get(Long reviewID) {
        Review review = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_REVIEW)) {

            statement.setLong(1, reviewID);
            ResultSet resultSet = null;
            try {
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    review = parseReviewResultSet(resultSet);
                }
            } catch (SQLException e) {
                throw new SQLException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error retrieving review record from database.", e);
            return null;
        }
        return review;
    }

    /**
     * Updates review data in database
     *
     * @param review review entity to update
     */
    public void update(Review review) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_REVIEW)) {

            statement.setLong(1, review.getUserId());
            statement.setLong(2, review.getMovieId());
            statement.setDate(3, review.getPostDate());
            statement.setString(4, review.getTitle());
            statement.setInt(5, review.getRating());
            statement.setString(6, review.getReviewText());
            statement.setLong(7, review.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Error updating review data.", e);
        }
    }

    /**
     * Searches for review with specified movieID in database
     *
     * @param movieID ID of movie which this review refers to
     * @return List of <code>Review</code>objects if found any. Otherwise returns empty List.
     */
    public List<Review> getReviewsByMovieId(Long movieID) {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_REVIEWS_BY_MOVIE_ID)) {

            statement.setLong(1, movieID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Review review;
                    review = parseReviewResultSet(resultSet);
                    reviews.add(review);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error retrieving reviews for movie. Movie ID: " + movieID, e);
            return null;
        }
        return reviews;
    }

    /**
     * Deletes review record from database
     *
     * @param reviewID ID of review to be removed from database
     * @return <b>true</b> if review has been successfully deleted. Otherwise returns <b>false</b>
     */
    public boolean delete(Long reviewID) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_REVIEW)) {

            statement.setLong(1, reviewID);
            int afterUpdate = statement.executeUpdate();
            if (afterUpdate >= 1) {
                return true;
            }

        } catch (SQLException e) {
            LOGGER.error("Error deleting review record from database.", e);
            return false;
        }
        return false;
    }

}
