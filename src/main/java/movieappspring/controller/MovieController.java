package movieappspring.controller;

import movieappspring.PrincipalUtil;
import movieappspring.entities.Movie;
import movieappspring.entities.Review;
import movieappspring.entities.User;
import movieappspring.entities.dto.MovieTransferObject;
import movieappspring.entities.dto.ReviewTransferObject;
import movieappspring.entities.util.MovieContainer;
import movieappspring.entities.util.PagedEntity;
import movieappspring.exception.OnGetNullException;
import movieappspring.exception.OnReviewCreateNullException;
import movieappspring.service.MovieService;
import movieappspring.service.ReviewService;
import movieappspring.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/movies")
public class MovieController {

    private final static Logger LOGGER = LogManager.getLogger();
    private static final String DEFAULT_PAGE_AS_STRING = "1";
    private static final Integer RECORDS_PER_PAGE = 5;
    private MovieService movieService;
    private ReviewService reviewService;
    private UserService userService;

    @Autowired
    public MovieController(MovieService movieService, ReviewService reviewService, UserService userService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView movies(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE_AS_STRING) Integer page)
            throws OnGetNullException {

        if (page < 1) {
            return new ModelAndView("redirect:/movies");
        }
        ModelAndView modelAndView = new ModelAndView("home");
        PagedEntity pagedMovies = movieService.getAllMoviesLimit((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        List<Movie> movies = (List<Movie>) pagedMovies.getEntity();
        List<MovieTransferObject> movieTransferObjects = movies.stream()
                .map(MovieTransferObject::new)
                .collect(Collectors.toList());
        int numberOfRecords = pagedMovies.getNumberOfRecords();
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);

        modelAndView.addObject("movies", movieTransferObjects);
        modelAndView.addObject("numberOfPages", numberOfPages);
        modelAndView.addObject("currentPage", page);
        return modelAndView;

    }

    @RequestMapping(value = "/{movieId}", method = RequestMethod.GET)
    public ModelAndView movie(@PathVariable Long movieId) throws OnGetNullException {
        if (movieId > 0 && movieId <= movieService.getMaxMovieId() && movieService.ifMovieExists(movieId)) {
            ModelAndView modelAndView = new ModelAndView("movie");

            MovieContainer movieContainer = completeMovie(movieId);

            modelAndView.addObject("postedReview", new ReviewTransferObject());
            modelAndView.addObject("movieContainer", movieContainer);
            return modelAndView;
        }
        return new ModelAndView("redirect:/movies");
    }

    @RequestMapping(value = "/{movieId}", method = RequestMethod.POST)
    public ModelAndView postReview(@PathVariable Long movieId,
                                   @Validated @ModelAttribute("postedReview") ReviewTransferObject reviewTransferObject,
                                   Errors errors) throws OnReviewCreateNullException, OnGetNullException {
        if (movieId < 1 && movieId > movieService.getMaxMovieId() && !movieService.ifMovieExists(movieId)) {
            return new ModelAndView("redirect:/movies");
        }
        if (errors.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("movie");
            MovieContainer movieContainer = completeMovie(movieId);
            modelAndView.addObject("movieContainer", movieContainer);
            return modelAndView;
        }

        Long currentUserId = PrincipalUtil.getCurrentPrincipal().getId();
        Date postDate = new Date(new java.util.Date().getTime());

        Review review = new Review(reviewTransferObject);
        review.setMovieId(movieId);
        review.setUserId(currentUserId);
        review.setPostDate(postDate);
        reviewService.createReview(review);
        updateMovieRating(movieId, reviewTransferObject.getRating());

        return new ModelAndView("redirect:/movies/" + movieId);
    }

    /**
     * Helper method to get and pack movie data, users and their reviews into one single <code>MovieContainer</code>
     * object for convenience.
     *
     * @param movieId id of movie for witch to retrieve data
     * @return <code>MovieContainer</code> object with all movie-related data
     * @see MovieContainer
     */
    private MovieContainer completeMovie(Long movieId) throws OnGetNullException {
        MovieContainer movieContainer = new MovieContainer();
        Movie movie = movieService.getMovieByID(movieId);
        MovieTransferObject movieTransferObject = new MovieTransferObject(movie);
        List<Review> reviews = reviewService.getReviewsByMovieId(movieId);
        reviews.sort((r1, r2) -> r2.getPostDate().compareTo(r1.getPostDate()));
        Map<Long, Object> users = new HashMap<>();
        if (reviews.size() > 0) {
            for (Review review : reviews) {
                if (review != null)
                    if (review.getUserId() != null)
                        if (review.getUserId() > 0) {
                            User user = userService.getUserById(review.getUserId());
                            users.put(review.getUserId(), user);
                        }
            }
        }
        movieContainer.setMovieTransferObject(movieTransferObject);
        movieContainer.setReviews(reviews);
        movieContainer.setUsers(users);
        return movieContainer;
    }

    /**
     * Updates movie current rating taking in account new rating put by user
     *
     * @param movieId id of movie to update rating
     * @param rating  new rating set by user
     */
    private void updateMovieRating(Long movieId, Integer rating) throws OnGetNullException {
        Movie movieToUpdate = movieService.getMovieByID(movieId);
        List<Review> reviews = reviewService.getReviewsByMovieId(movieId);

        if (!reviews.isEmpty()) {
            movieToUpdate.setRating(recountRating(reviews, rating));
            movieService.updateMovie(movieToUpdate);
        } else {
            movieToUpdate.setRating(Double.valueOf(rating));
            movieService.updateMovie(movieToUpdate);
        }
    }

    /**
     * Recounts current movie rating taking in account new rating
     *
     * @param reviews <code>List</code> of <code>Review</code> objects to be parsed for ratings
     * @param rating  new rating to add to summary
     * @return new rating value
     */
    private Double recountRating(List<Review> reviews, Integer rating) {
        Double totalRating = 0d;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        Double newRating = (totalRating + rating) / (reviews.size() + 1);
        Double newRatingFormatted = null;
        try {
            newRatingFormatted = Double.valueOf(df.format(newRating));
        } catch (NumberFormatException e) {
            LOGGER.error("Error parsing rating during movie rating update.", e);
        }
        return (newRatingFormatted != null) ? newRatingFormatted : newRating;
    }

}