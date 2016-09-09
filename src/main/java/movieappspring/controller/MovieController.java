package movieappspring.controller;

import movieappspring.entities.Movie;
import movieappspring.entities.PagedEntity;
import movieappspring.entities.Review;
import movieappspring.entities.User;
import movieappspring.service.MovieService;
import movieappspring.service.ReviewService;
import movieappspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/movies")
public class MovieController {

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
    public ModelAndView movies(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE_AS_STRING) Integer page) throws SQLException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");

        if (page <= 0) {
            // TODO some logic
        }

        PagedEntity pagedMovies = movieService.getAllMoviesLimit((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        List<Movie> movies = (List<Movie>) pagedMovies.getEntity();
        int numberOfRecords = pagedMovies.getNumberOfRecords();
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);

        modelAndView.addObject("movies", movies);
        modelAndView.addObject("numberOfPages", numberOfPages);
        modelAndView.addObject("currentPage", page);
        return modelAndView;
    }

    @RequestMapping(value = "/{movieId}", method = RequestMethod.GET)
    public ModelAndView movie(@PathVariable Long movieId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("movie");
        // TODO handle invalid movieId
        // mb add some kind of "movie container"

        Movie movie = movieService.getMovieByID(movieId);
        List<Review> reviews = reviewService.getReviewsByMovieId(movieId);
        reviews.sort((r1, r2) -> r2.getPostDate().compareTo(r1.getPostDate()));
        Map<Long, String> users = new HashMap<>();
        if (reviews.size() > 0) {
            for (Review review : reviews) {
                // TODO check this spot. Tricky place
                User user = userService.getUserById(review.getId());
                users.put(review.getUserId(), user.getName());
            }
        }


        modelAndView.addObject("movie", movie);
        modelAndView.addObject("users", users);
        modelAndView.addObject("reviews", reviews);
        return modelAndView;
    }

}
