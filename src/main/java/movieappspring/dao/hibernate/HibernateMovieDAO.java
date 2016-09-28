package movieappspring.dao.hibernate;

import movieappspring.dao.MovieDAO;
import movieappspring.entities.Movie;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * Class that accesses the database and performs any activity connected with database using hibernate.
 * <p>
 * This particular class deals with movie data in database.
 */
@Repository
@Transactional
public class HibernateMovieDAO implements MovieDAO {

    private static final String SQL_SELECT_ALL = "SELECT SQL_CALC_FOUND_ROWS * FROM movie";
    private static final String SQL_SELECT_FOUND_ROWS = "SELECT FOUND_ROWS()";
    private static final String SQL_SELECT_LIKE = "SELECT SQL_CALC_FOUND_ROWS * FROM movie WHERE moviename LIKE ?";
    private static final String SQL_SELECT_ORDER_BY = "SELECT SQL_CALC_FOUND_ROWS * FROM movie ORDER BY ";
    private SessionFactory sessionFactory;
    private Integer numberOfRecords;

    @Autowired
    public HibernateMovieDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Creates a record for new movie in database
     *
     * @param movie <code>Movie</code> object to be added to database
     * @return ID (specified by mapping in <code>Movie</code> class) of created movie record in database.
     */
    @Override
    public Long create(Movie movie) {
        return (Long) sessionFactory.getCurrentSession().save(movie);
    }

    /**
     * Searches for movie with specified ID in database
     *
     * @param movieId ID of movie to be found
     * @return Movie entity object if movie with given ID is found in database. Otherwise returns null.
     */
    @Override
    public Movie get(Long movieId) {
        return sessionFactory.getCurrentSession().get(Movie.class, movieId);
    }

    /**
     * Updates movie data in database
     *
     * @param movie movie entity to update
     */
    @Override
    public void update(Movie movie) {
        sessionFactory.getCurrentSession().update(movie);
    }

    /**
     * Deletes movie record from database
     *
     * @param movie movie to be removed from database
     */
    @Override
    public void delete(Movie movie) {
        sessionFactory.getCurrentSession().delete(movie);
    }

    /**
     * Returns records for all movies in database
     *
     * @return List of Movie objects if any found. Otherwise returns an empty list
     */
    @Override
    public List<Movie> getAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM Movie", Movie.class).list();
    }

    /**
     * Returns some part of movies from database.
     *
     * @param offset   starting position of select query
     * @param noOfRows desired number of records per page
     * @return List of Movie objects in given range if any movies found. Otherwise returns empty list
     */
    @Override
    public List<Movie> getAllLimit(Integer offset, Integer noOfRows) {
        List<Movie> movies = sessionFactory.getCurrentSession()
                .createNativeQuery(SQL_SELECT_ALL, Movie.class)
                .setFirstResult(offset)
                .setMaxResults(noOfRows).list();
        this.numberOfRecords = ((BigInteger) sessionFactory.getCurrentSession()
                .createNativeQuery(SQL_SELECT_FOUND_ROWS)
                .uniqueResult()).intValue();
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
    @Override
    public List<Movie> getMoviesLike(String movieName, Integer offset, Integer noOfRows) {
        List<Movie> movies = sessionFactory.getCurrentSession()
                .createNativeQuery(SQL_SELECT_LIKE, Movie.class)
                .setParameter(1, movieName + "%")
                .setFirstResult(offset)
                .setMaxResults(noOfRows).list();
        this.numberOfRecords = ((BigInteger) sessionFactory.getCurrentSession()
                .createNativeQuery(SQL_SELECT_FOUND_ROWS)
                .uniqueResult()).intValue();
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
    @Override
    public List<Movie> getMoviesSorted(Integer offset, Integer noOfRows, String orderBy, Boolean isDesc) {
        List<Movie> movies = sessionFactory.getCurrentSession()
                .createNativeQuery(SQL_SELECT_ORDER_BY + orderBy + (isDesc ? " DESC" : " ASC"), Movie.class)
                .setFirstResult(offset)
                .setMaxResults(noOfRows).list();
        this.numberOfRecords = ((BigInteger) sessionFactory.getCurrentSession()
                .createNativeQuery(SQL_SELECT_FOUND_ROWS)
                .uniqueResult()).intValue();
        return movies;
    }

    /**
     * Method used in pagination. Retrieves number of records for current query
     *
     * @return number of records retrieved during last query
     */
    @Override
    public Integer getNumberOfRecords() {
        return this.numberOfRecords;
    }

    /**
     * Searches for maximum movie id stored in database
     *
     * @return <code>Long</code> value representing largest movie id in database if any movies found. Else returns null.
     */
    @Override
    public Long maxMovieId() {
        return (Long) sessionFactory.getCurrentSession()
                .createQuery("SELECT id FROM Movie ORDER BY id DESC")
                .setMaxResults(1)
                .uniqueResult();
    }

    /**
     * Checks whether movie with such id exists in database
     *
     * @param movieId id of movie to look for
     * @return <b>true</b> is movie with such id exists in database. Otherwise returns <b>false</b>
     */
    @Override
    public boolean ifMovieExists(Long movieId) {
        Long id = (Long) sessionFactory.getCurrentSession()
                .createQuery("SELECT id FROM Movie WHERE id = :id")
                .setParameter("id", movieId)
                .uniqueResult();
        return id != null && id > 0;
    }

}