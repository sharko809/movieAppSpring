package movieappspring.service;

import movieappspring.dao.MovieDAO;
import movieappspring.entities.Movie;
import movieappspring.entities.util.PagedEntity;
import movieappspring.exception.OnGetNullException;
import movieappspring.exception.OnMovieCreateNullException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
     * @param movie movie to be added to database
     * @return ID of created movie record in database. If record hasn't been created returns 0.
     */
    public Long addMovie(Movie movie) throws OnMovieCreateNullException {
        Long movieID = movieDAO.create(movie);
        if (movieID == null) {
            LOGGER.error("Unable to create movie. DAO method returned null.");
            throw new OnMovieCreateNullException("Unable to create movie.", movie);
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
    public Movie getMovieByID(Long movieID) throws OnGetNullException {
        Movie movie = movieDAO.get(movieID);
        if (movie == null) {
            LOGGER.error("Unable to get movie by id: " + movieID + ". DAO method returned null.");
            throw new OnGetNullException("Unable to get movie.");
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
     * @param movie movie to be removed from database
     */
    public void deleteMovie(Movie movie) {
        movieDAO.delete(movie);
    }

    /**
     * Returns records for all movies in database
     *
     * @return List of Movie objects if any found. Otherwise returns an empty list
     * @see Movie
     */
    @Deprecated
    public List<Movie> getAllMovies() throws OnGetNullException {
        List<Movie> movies = movieDAO.getAll();
        if (movies == null) {
            LOGGER.error("Unable to get all movies. DAO method returned null");
            throw new OnGetNullException("Error during retireving movies.");
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
    public PagedEntity getAllMoviesLimit(Integer offset, Integer noOfRows) throws OnGetNullException {
        List<Movie> movies = movieDAO.getAllLimit(offset, noOfRows);
        Integer numberOfRecords = movieDAO.getNumberOfRecords();
        if (movies == null || numberOfRecords == null) {
            LOGGER.error("Unable to get movies for offset " + offset + ", number of movies " + noOfRows + ".");
            throw new OnGetNullException("Error during searching the movie");
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
    public PagedEntity searchMovies(String movieName, Integer offset, Integer noOfRows) throws OnGetNullException {
        List<Movie> movies = movieDAO.getMoviesLike(movieName, offset, noOfRows);
        Integer numberOfRecords = movieDAO.getNumberOfRecords();
        if (movies == null || numberOfRecords == null) {
            LOGGER.error("Unable to get movies for offset " + offset + ", number of movies " + noOfRows +
                    " and search query " + movieName);
            throw new OnGetNullException("Error during searching the movie");
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
    public PagedEntity getMoviesSorted(Integer offset, Integer noOfRows, String orderBy, Boolean isDesc)
            throws OnGetNullException {
        List<Movie> movies = movieDAO.getMoviesSorted(offset, noOfRows, orderBy, isDesc);
        Integer numberOfRecords = movieDAO.getNumberOfRecords();
        if (movies == null || numberOfRecords == null) {
            LOGGER.error("Unable to get movies for offset " + offset + ", number of movies " + noOfRows +
                    " and sorted by " + orderBy + ". DAO method returned " + movies + " movies and " + numberOfRecords
                    + " number of records");
            throw new OnGetNullException("Unable to sort movies.");
        }
        PagedEntity pagedMovies = new PagedEntity();
        pagedMovies.setEntity(movies);
        pagedMovies.setNumberOfRecords(numberOfRecords);
        return pagedMovies;
    }

    public Long getMaxMovieId() throws OnGetNullException {
        Long movieId = movieDAO.maxMovieId();
        if (movieId == null) {
            LOGGER.error("Unable to get max movie id. DAO method returned null.");
            throw new OnGetNullException("Unable to get max movie id.");
        }
        return movieId;
    }

    public boolean ifMovieExists(Long movieId) {
        return movieDAO.ifMovieExists(movieId);
    }

}