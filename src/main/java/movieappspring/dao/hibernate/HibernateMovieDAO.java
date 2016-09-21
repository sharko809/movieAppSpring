package movieappspring.dao.hibernate;

import movieappspring.dao.MovieDAO;
import movieappspring.entities.Movie;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * Created by dsharko on 9/20/2016.
 */
@Repository
@Transactional
public class HibernateMovieDAO implements MovieDAO {

    private SessionFactory sessionFactory;
    private Integer numberOfRecords;

    @Autowired
    public HibernateMovieDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long create(String movieName, String director, Date releaseDate, String posterURL, String trailerUrl, Double rating, String description) {
        Movie movie = new Movie();
        movie.setMovieName(movieName);
        movie.setDirector(director);
        movie.setReleaseDate(releaseDate);
        movie.setPosterURL(posterURL);
        movie.setTrailerURL(trailerUrl);
        movie.setRating(rating);
        movie.setDescription(description);
        sessionFactory.getCurrentSession().save(movie);
        System.out.println(movie.getId());
        return movie.getId();
    }

    @Override
    public Movie get(Long movieId) {
        return ((Movie) sessionFactory.getCurrentSession().
                createQuery("from Movie WHERE id = :id").setParameter("id", movieId).uniqueResult());
    }

    @Override
    public void update(Movie movie) {
        sessionFactory.getCurrentSession().update(movie);
    }

    @Override
    public boolean delete(Long movieID) {
        sessionFactory.getCurrentSession().delete(get(movieID));// TODO looks like shit
//        return false;
        return true;
    }

    @Override
    public List<Movie> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Movie").list();
    }

    @Override
    public List<Movie> getAllLimit(Integer offset, Integer noOfRows) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from Movie")
                .setFirstResult(offset)
                .setMaxResults(noOfRows);
        List<Movie> movies = query.list();
        this.numberOfRecords = Integer.valueOf(sessionFactory.getCurrentSession()
                .createQuery("SELECT count(*) FROM Movie").uniqueResult().toString());// TODO looks like shit
        // TODO exceptions?
        return movies;
    }

    @Override
    public List<Movie> getMoviesLike(String movieName, Integer offset, Integer noOfRows) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from Movie where movieName like :title")
                .setParameter("title", movieName)
                .setFirstResult(offset)
                .setMaxResults(noOfRows);
        List<Movie> movies = query.list();
        movies.forEach(movie -> System.out.println(movie.getMovieName()));
        this.numberOfRecords = Integer.valueOf(sessionFactory.getCurrentSession()
                .createQuery("SELECT count(*) FROM Movie").uniqueResult().toString());// TODO todo
        return movies;
    }

    @Override
    public List<Movie> getMoviesSorted(Integer offset, Integer noOfRows, String orderBy, Boolean isDesc) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from Movie ORDER BY rating " + (isDesc ? "DESC" : "ASC"))
                .setFirstResult(offset)
                .setMaxResults(noOfRows);
//                .setParameter("sort", orderBy);
        System.out.println(query.getQueryString());
        List<Movie> movies = query.list();
        movies.forEach(o -> {
            System.out.println(o.getRating());;
        });
        return movies;// TODO todo
    }

    @Override
    public Integer getNumberOfRecords() {
        return this.numberOfRecords;
    }

    @Override
    public Long maxMovieId() {
        return (Long) sessionFactory.getCurrentSession()
                .createQuery("SELECT id FROM Movie ORDER BY id DESC")
                .setMaxResults(1)
                .uniqueResult();
    }

    @Override
    public boolean ifMovieExists(Long movieId) {
        Long id = (Long) sessionFactory.getCurrentSession()
                .createQuery("select id from Movie where id = :id")
                .setParameter("id", movieId)
                .uniqueResult();
        return id != null && id > 0;
    }

}