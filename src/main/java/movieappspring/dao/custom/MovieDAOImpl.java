package movieappspring.dao.custom;

import movieappspring.dao.ConnectionManager;
import movieappspring.dao.MovieDAO;
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
public class MovieDAOImpl implements MovieDAO {

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

    /**
     * Query for retrieving part of movie records specified by "LIMIT" and ordered in desired way
     */
    private static final String SQL_GET_MOVIES_SORTED_BY = "SELECT SQL_CALC_FOUND_ROWS * FROM MOVIE " +
            "ORDER BY @ LIMIT ?, ?";

    private static final String SQL_GET_MAX_ID = "SELECT id FROM movie ORDER BY id DESC LIMIT 1";

    private Integer numberOfRecords;

    private ConnectionManager connectionManager;

    @Autowired
    public MovieDAOImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Helper method to parse result set
     *
     * @param resultSet result set to parse
     * @return Movie object with filled fields
     */
    private Movie parseMovieResultSet(ResultSet resultSet) {
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
            LOGGER.error("Error parsing movie result set.", e);
            return null;
        }
        return movie;
    }

    /**
     * Creates a record for new movie in database
     *
     * @param movie movie to be added to database
     * @return ID of created movie record in database. If record hasn't been created returns null.
     */
    @Override
    public Long create(Movie movie) {
        Long movieID = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_MOVIE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, movie.getMovieName());
            statement.setString(2, movie.getDirector());
            statement.setDate(3, movie.getReleaseDate());
            statement.setString(4, movie.getPosterURL());
            statement.setString(5, movie.getTrailerURL());
            statement.setDouble(6, movie.getRating());
            statement.setString(7, movie.getDescription());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    movieID = resultSet.getLong(1);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error creating new movie record. ", e);
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
            LOGGER.error("Error retrieving movie from database. ", e);
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
            LOGGER.error("Error updating movie " + movie.getMovieName() + " ID: " + movie.getId(), e);
        }
    }

    /**
     * Deletes movie record from database
     *
     * @param movie movie to be removed from database
     */
    @Override
    public void delete(Movie movie) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_MOVIE)) {

            statement.setLong(1, movie.getId());
            int afterUpdate = statement.executeUpdate();
            if (afterUpdate < 1) {
                LOGGER.error("No movie deleted.");
            }

        } catch (SQLException e) {
            LOGGER.error("Error deleting movie record from database. ", e);
        }
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
            LOGGER.error("Error retrieving all movies from database. ", e);
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
                LOGGER.error("Error during working with result.", e);
                throw new SQLException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error retrieving movie list with offset " + offset +
                    " and number of records " + noOfRows + ". ", e);
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

            if (movieName.isEmpty()) {
                return movies;
            }

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
                LOGGER.error("Error during working with result.", e);
                throw new SQLException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error searching movie like \'" + movieName + "\' with offset " + offset +
                    " and number of records " + noOfRows + ". ", e);
            return null;
        }

        return movies;
    }

    /**
     * Returns some part of movies sorted in particular way from database.
     *
     * @param offset   starting position of select query
     * @param noOfRows desired number of records per page
     * @param orderBy  column by which soring is performed
     * @param isDesc   <b>true</b> if you want descending sorting
     * @return List of <code>Movie</code> objects in given range if any users found. Otherwise returns empty list
     */
    public List<Movie> getMoviesSorted(Integer offset, Integer noOfRows, String orderBy, Boolean isDesc) {
        List<Movie> movies = new ArrayList<>();
        String query = makeSortQuery(SQL_GET_MOVIES_SORTED_BY, orderBy, isDesc);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

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
                LOGGER.error("Error during working with result.", e);
                throw new SQLException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error retrieving sorted movie list with offset " + offset +
                    " and number of records " + noOfRows + ". ", e);
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

    /**
     * Searches for maximum movie id stored in database
     *
     * @return <code>Long</code> value representing largest movie id in database if any movies found. Else returns null.
     */
    public Long maxMovieId() {
        Long userId = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_MAX_ID)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getLong(1);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Can't get max movie id.", e);
            return null;
        }
        return userId;
    }

    /**
     * Checks whether movie with such id exists in database
     *
     * @param movieId id of movie to look for
     * @return <b>true</b> is movie with such id exists in database. Otherwise returns <b>false</b>
     */
    public boolean ifMovieExists(Long movieId) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_MOVIE_BY_ID)) {

            statement.setLong(1, movieId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error during checking movie id.", e);
            return false;
        }
        return false;
    }

    /**
     * Helper method for creating proper query string
     *
     * @param query        query itself
     * @param orderBy      column to order by
     * @param isDescending is sorting descending
     * @return properly formatted query string
     */
    private String makeSortQuery(String query, String orderBy, Boolean isDescending) {
        return query.replace("@", isDescending ? orderBy + " DESC " : orderBy);
    }

}