package movieappspring.service;

import movieappspring.dao.MovieDAO;
import movieappspring.entities.Movie;
import movieappspring.entities.PagedEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Class containing all methods to interact with movies
 */
public class MovieService {

    private static final Logger LOGGER = LogManager.getLogger();
    private MovieDAO movieDAO;

    public MovieService(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    /**
     * Calls for DAO method to create a new movie record
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
    public Long addMovie(String movieName, String director, Date releaseDate, String posterURL,
                         String trailerUrl, Double rating, String description) throws SQLException {
        Long movieID = movieDAO.create(movieName, director, releaseDate, posterURL, trailerUrl, rating, description);
        if (movieID == null) {
            // TODO create exception
        }
        return movieID;
    }

    /**
     * Get movie with specified ID from database
     *
     * @param movieID ID of movie to be found
     * @return Movie entity object if movie with given ID is found in database. Otherwise returns null
     * @see Movie
     */
    public Movie getMovieByID(Long movieID) {
        Movie movie = movieDAO.get(movieID);
        if (movie == null) {
            // TODO create exception
        }
        return movie;
    }

    /**
     * Updates movie data in database
     *
     * @param movie movie entity to update
     */
    public void updateMovie(Movie movie) {
        movieDAO.update(movie);
    }

    /**
     * Deletes movie record from database
     *
     * @param movieID ID of movie to be removed from database
     * @return <b>true</b> if movie has been successfully deleted. Otherwise returns <b>false</b>
     */
    public boolean deleteMovie(Long movieID) {
        return movieDAO.delete(movieID);
    }

    /**
     * Returns records for all movies in database
     *
     * @return List of Movie objects if any found. Otherwise returns an empty list
     * @see Movie
     */
    public List<Movie> getAllMovies() {
        List<Movie> movies = movieDAO.getAll();
        if (movies == null) {
            // TODO create exception
        }
        return movies;
    }

    /**
     * Method used for pagination. Using offset and desired number of records returns some part of movies from database.
     *
     * @param offset   starting position of select query
     * @param noOfRows desired number of records per page
     * @return PagedEntity object storing List of Movie objects in given range and Number of Records in database if
     * any movies found. Otherwise returns PagedEntity object with empty list and null records value
     * @see PagedEntity
     */
    public PagedEntity getAllMoviesLimit(Integer offset, Integer noOfRows) {
        List<Movie> movies = movieDAO.getAllLimit(offset, noOfRows);
        Integer numberOfRecords = movieDAO.getNumberOfRecords();
        if (movies == null || numberOfRecords == null) {
            // TODO create exception
        }
        PagedEntity pagedMovies = new PagedEntity();
        pagedMovies.setEntity(movies);
        pagedMovies.setNumberOfRecords(numberOfRecords);
        return pagedMovies;
    }

    /**
     * Gets all movies with matching name from database. Method is used for paginating results as well.
     *
     * @param movieName movie name to look for
     * @param offset    starting position of select query
     * @param noOfRows  desired number of records per page
     * @return PagedEntity object storing List of Movie objects in given range and Number of Records in database if
     * any movies found. Otherwise returns PagedEntity object with empty list and null records value
     * @see PagedEntity
     */
    public PagedEntity searchMovies(String movieName, Integer offset, Integer noOfRows) {
        List<Movie> movies = movieDAO.getMoviesLike(movieName, offset, noOfRows);
        Integer numberOfRecords = movieDAO.getNumberOfRecords();
        if (movies == null || numberOfRecords == null) {
            // TODO create exception
        }
        PagedEntity pagedMovies = new PagedEntity();
        pagedMovies.setEntity(movies);
        pagedMovies.setNumberOfRecords(numberOfRecords);
        return pagedMovies;
    }

    /**
     * Using offset and desired number of records returns some part of movie records from database.
     *
     * @param offset   starting position of select query
     * @param noOfRows desired number of records per page
     * @param orderBy  fields by which soring is performed
     * @param isDesc   <b>true</b> if you want descending sorting
     * @return PagedEntity object storing List of <code>Movie</code> objects in given range and Number of Records in
     * database if any movie records found. Otherwise returns PagedEntity object with empty list and null records value
     * @see PagedEntity
     */
    public PagedEntity getMoviesSorted(Integer offset, Integer noOfRows, String orderBy, Boolean isDesc) {
        List<Movie> movies = movieDAO.getMoviesSorted(offset, noOfRows, orderBy, isDesc);
        Integer numberOfRecords = movieDAO.getNumberOfRecords();
        if (movies == null || numberOfRecords == null) {
            // TODO create exception
        }
        PagedEntity pagedMovies = new PagedEntity();
        pagedMovies.setEntity(movies);
        pagedMovies.setNumberOfRecords(numberOfRecords);
        return pagedMovies;
    }

    public Long getMaxMovieId() {
        Long movieId = movieDAO.maxMovieId();
        if (movieId == null || movieId < 1) {
            // TODO handle exception
        }
        return movieId;
    }

    public boolean ifMovieExists(Long movieId) {
        return movieDAO.ifMovieExists(movieId);
    }

}