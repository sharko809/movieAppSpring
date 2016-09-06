package movieappspring.dao;

import movieappspring.entities.Review;
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
     * @throws SQLException
     */
    private static Review parseReviewResultSet(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getLong("id"));
        review.setUserId(resultSet.getLong("userid"));
        review.setMovieId(resultSet.getLong("movieid"));
        review.setPostDate(resultSet.getDate("postdate"));
        review.setTitle(resultSet.getString("reviewtitle"));
        review.setRating(resultSet.getInt("rating"));
        review.setReviewText(resultSet.getString("reviewtext"));
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
     * @throws SQLException
     */
    public Long create(Long userID, Long movieID, Date postDate, String reviewTitle, Integer rating, String reviewText) throws SQLException {
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

        }
        return reviewID;
    }

    /**
     * Searches for review with specified ID ing database
     *
     * @param reviewID ID of user to be found
     * @return Review entity object if review with given ID is found in database. Otherwise returns null.
     * @throws SQLException
     */
    public Review get(Long reviewID) throws SQLException {
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

        }
        return review;
    }

    /**
     * Updates review data in database
     *
     * @param review review entity to update
     * @throws SQLException
     */
    public void update(Review review) throws SQLException {
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

        }
    }

    /**
     * Searches for review with specified movieID in database
     *
     * @param movieID ID of movie which this review refers to
     * @return List of Review objects if found any. Otherwise returns empty List.
     * @throws SQLException
     */
    public List<Review> getReviewsByMovieId(Long movieID) throws SQLException {
        List<Review> reviews = new ArrayList<Review>();
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

        }
        return reviews;
    }

    /**
     * Deletes review record from database
     *
     * @param reviewID ID of review to be removed from database
     * @return <b>true</b> if review has been successfully deleted. Otherwise returns <b>false</b>
     * @throws SQLException
     */
    public boolean delete(Long reviewID) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_REVIEW)) {

            statement.setLong(1, reviewID);
            int afterUpdate = statement.executeUpdate();
            if (afterUpdate >= 1) {
                return true;
            }

        }
        return false;
    }

}
