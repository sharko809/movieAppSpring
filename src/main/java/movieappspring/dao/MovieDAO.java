package movieappspring.dao;

import movieappspring.entities.Movie;

import java.util.List;

/**
 * Interface declaring witch methods should be used in each particular Movie DAO implementation
 */
public interface MovieDAO {

    Long create(Movie movie);

    Movie get(Long movieId);

    void update(Movie movie);

    void delete(Movie movie);

    List<Movie> getAll();

    List getAllLimit(Integer offset, Integer noOfRows);

    List<Movie> getMoviesLike(String movieName, Integer offset, Integer noOfRows);

    List<Movie> getMoviesSorted(Integer offset, Integer noOfRows, String orderBy, Boolean isDesc);

    Integer getNumberOfRecords();

    Long maxMovieId();

    boolean ifMovieExists(Long movieId);

}