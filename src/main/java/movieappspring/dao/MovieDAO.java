package movieappspring.dao;

import movieappspring.entities.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that accesses the database and performs any activity connected with database.
 * <p>
 * This particular class deals with movie data in database.
 */
public class MovieDAO {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Query for counting rows found by queries
     */
    private static final String COUNT_FOUND_ROWS = "SELECT FOUND_ROWS()";

    /**
     * Query for retrieving all movies records from database
     */
    private static final String SQL_GET_ALL_MOVIES = "SELECT * FROM MOVIE";

    /**
     * Query for retrieving part of movies records specified by "LIMIT"
     */
    private static final String SQL_GET_ALL_MOVIES_WITH_LIMIT = "SELECT SQL_CALC_FOUND_ROWS * FROM MOVIE LIMIT ?, ?";

    /**
     * Query for selecting movie with given ID from database
     */
    private static final String SQL_GET_MOVIE_BY_ID = "SELECT * FROM MOVIE WHERE ID = ?";

    /**
     * Query for adding new record with movie data to database
     */
    private static final String SQL_ADD_MOVIE = "INSERT INTO MOVIE " +
            "(moviename, director, releasedate, posterurl, trailerurl, rating, description) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Query for updating movie record in database
     */
    private static final String SQL_UPDATE_MOVIE = "UPDATE MOVIE SET " +
            "moviename = ?, " +
            "director = ?, " +
            "releasedate = ?, " +
            "posterurl = ?, " +
            "trailerurl = ?, " +
            "rating = ?, " +
            "description = ? WHERE ID = ?";

    /**
     * Query for removing movie with given ID from database
     */
    private static final String SQL_DELETE_MOVIE = "DELETE FROM MOVIE WHERE ID = ?";

    /**
     * Query for retrieving movie with specified title from database
     */
    private static final String SQL_SEARCH_MOVIE_BY_TITLE = "SELECT SQL_CALC_FOUND_ROWS * FROM MOVIE WHERE " +
            "moviename LIKE ? LIMIT ?, ?";

    private Integer numberOfRecords;

    private ConnectionManager connectionManager;

    @Autowired
    public MovieDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Helper method to parse result set
     *
     * @param resultSet result set to parse
     * @return Movie object with filled fields
     */
    private static Movie parseMovieResultSet(ResultSet resultSet) {
        Movie movie = new Movie();
        try {
            movie.setId(resultSet.getLong("ID"));
            movie.setMovieName(resultSet.getString("moviename"));
            movie.setDirector(resultSet.getString("director"));
            movie.setReleaseDate(resultSet.getDate("releasedate"));
            movie.setPosterURL(resultSet.getString("posterurl"));
            movie.setTrailerURL(resultSet.getString("trailerurl"));
            movie.setRating(resultSet.getDouble("rating"));
            movie.setDescription(resultSet.getString("description"));
        } catch (SQLException e) {
            LOGGER.error("Error parsing movie result set. " + e, e);
            return null;
        }
        return movie;
    }

    /**
     * Creates a record for new movie in database
     *
     * @param movieName   desired movie title
     * @param director    movie director
     * @param releaseDate movie release date in  "yyyy-MM-dd" format
     * @param posterURL   URL leading to movie poster
     * @param trailerUrl  URL leading to embed video
     * @param rating      movie rating. Set to 0 by default
     * @param description some description for the movie
     * @return ID of created movie record in database. If record hasn't been created returns 0.
     */
    public Long create(String movieName, String director, Date releaseDate,
                       String posterURL, String trailerUrl, Double rating, String description) {
        Long movieID = 0L;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_MOVIE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, movieName);
            statement.setString(2, director);
            statement.setDate(3, releaseDate);
            statement.setString(4, posterURL);
            statement.setString(5, trailerUrl);
            statement.setDouble(6, rating);
            statement.setString(7, description);
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    movieID = resultSet.getLong(1);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error creating new movie record. " + e, e);
            return null;
        }
        return movieID;
    }

    /**
     * Searches for movie with specified ID in database
     *
     * @param movieId ID of movie to be found
     * @return Movie entity object if movie with given ID is found in database. Otherwise returns null.
     */
    public Movie get(Long movieId) {
        Movie movie = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_MOVIE_BY_ID)) {

            statement.setLong(1, movieId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    movie = parseMovieResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error retrieving movie from database. " + e, e);
            return null;
        }
        return movie;
    }

    /**
     * Updates movie data in database
     *
     * @param movie movie entity to update
     */
    public void update(Movie movie) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MOVIE)) {

            statement.setString(1, movie.getMovieName());
            statement.setString(2, movie.getDirector());
            statement.setDate(3, movie.getReleaseDate());
            statement.setString(4, movie.getPosterURL());
            statement.setString(5, movie.getTrailerURL());
            statement.setDouble(6, movie.getRating());
            statement.setString(7, movie.getDescription());
            statement.setLong(8, movie.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Error updating movie. " + e, e);
        }
    }

    /**
     * Deletes movie record from database
     *
     * @param movieID ID of movie to be removed from database
     * @return <b>true</b> if movie has been successfully deleted. Otherwise returns <b>false</b>
     */
    public boolean delete(Long movieID) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_MOVIE)) {

            statement.setLong(1, movieID);
            int afterUpdate = statement.executeUpdate();
            if (afterUpdate >= 1) {
                return true;
            }

        } catch (SQLException e) {
            LOGGER.error("Error deleting movie record from database. " + e, e);
            return false;
        }
        return false;
    }

    /**
     * Returns records for all movies in database
     *
     * @return List of Movie objects if any found. Otherwise returns an empty list
     */
    public List<Movie> getAll() {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_MOVIES)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Movie movie;
                    movie = parseMovieResultSet(resultSet);
                    movies.add(movie);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error retrieving all movies from database. " + e, e);
            return null;
        }
        return movies;
    }

    /**
     * Returns some part of movies from database.
     *
     * @param offset   starting position of select query
     * @param noOfRows desired number of records per page
     * @return List of Movie objects in given range if any movies found. Otherwise returns empty list
     */
    public List<Movie> getAllLimit(Integer offset, Integer noOfRows) {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_MOVIES_WITH_LIMIT)) {

            statement.setInt(1, offset);
            statement.setInt(2, noOfRows);
            ResultSet resultSet = null;
            try {
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Movie movie;
                    movie = parseMovieResultSet(resultSet);
                    movies.add(movie);
                }
                resultSet.close();
                resultSet = statement.executeQuery(COUNT_FOUND_ROWS);
                if (resultSet.next()) {
                    this.numberOfRecords = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                throw new SQLException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error retrieving movie list with offset " + offset +
                    " and number of records " + noOfRows + ". " + e, e);
            return null;
        }
        return movies;
    }

    /**
     * Gets all movies (or some part of them) with matching name from database.
     *
     * @param movieName movie name to look for
     * @param offset    starting position of select query
     * @param noOfRows  desired number of records per page
     * @return List of Movie objects in given range if any movies found. Otherwise empty list
     */
    public List<Movie> getMoviesLike(String movieName, Integer offset, Integer noOfRows) {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SEARCH_MOVIE_BY_TITLE)) {

            statement.setString(1, movieName + "%");
            statement.setInt(2, offset);
            statement.setInt(3, noOfRows);
            ResultSet resultSet = null;
            try {
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Movie movie;
                    movie = parseMovieResultSet(resultSet);
                    movies.add(movie);
                }
                resultSet.close();
                resultSet = statement.executeQuery(COUNT_FOUND_ROWS);
                if (resultSet.next()) {
                    this.numberOfRecords = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                throw new SQLException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error searching movie like \'" + movieName + "\' with offset " + offset +
                    " and number of records " + noOfRows + ". " + e, e);
            return null;
        }

        return movies;
    }

    /**
     * Method used in pagination. Retrieves number of records for current query
     *
     * @return number of records retrieved during last query
     */
    public Integer getNumberOfRecords() {
        return this.numberOfRecords;
    }

}
