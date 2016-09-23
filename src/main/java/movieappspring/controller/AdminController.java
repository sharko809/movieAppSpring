package movieappspring.controller;

import movieappspring.PrincipalUtil;
import movieappspring.entities.Movie;
import movieappspring.entities.Review;
import movieappspring.entities.User;
import movieappspring.entities.dto.MovieTransferObject;
import movieappspring.entities.dto.UserTransferObject;
import movieappspring.entities.util.MovieContainer;
import movieappspring.entities.util.PagedEntity;
import movieappspring.security.PasswordManager;
import movieappspring.service.MovieService;
import movieappspring.service.ReviewService;
import movieappspring.service.UserService;
import movieappspring.validation.marker.CreateUserValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.groups.Default;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Pattern for displaying movie rating in specific format
     */
    private static final String DECIMAL_PATTERN = "#.##";

    /**
     * This value is used by default when page parameter is not found during pagination
     */
    private static final String DEFAULT_PAGE_AS_STRING = "1";

    /**
     * Default user sorting value in users page
     */
    private static final String DEFAULT_SORT_TYPE = "id";

    /**
     * Default value for descending parameter
     */
    private static final String DEFAULT_SORT_DESCENDING = "0";

    /**
     * Default redirect path for managemovies page
     */
    private static final String DEFAULT_MOVIES_REDIRECT = "/admin/managemovies";

    /**
     * Default redirect path for users page
     */
    private static final String DEFAULT_USERS_REDIRECT = "/admin/users";

    /**
     * Movie records per page
     */
    private static final Integer M_RECORDS_PER_PAGE = 10;

    /**
     * User records per page
     */
    private static final Integer U_RECORDS_PER_PAGE = 10;
    private MovieService movieService;
    private UserService userService;
    private ReviewService reviewService;
    private PasswordManager passwordManager;

    @Autowired
    public AdminController(MovieService movieService, UserService userService, ReviewService reviewService,
                           PasswordManager passwordManager) {
        this.movieService = movieService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.passwordManager = passwordManager;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView admin() {
        return new ModelAndView("admin");
    }

    @RequestMapping(value = "/addmovie", method = RequestMethod.GET)
    public ModelAndView addMovieView() {
        return new ModelAndView("addmovie", "movie", new MovieTransferObject());
    }

    @RequestMapping(value = "/addmovie", method = RequestMethod.POST)
    public ModelAndView addMovie(@Validated @ModelAttribute(name = "movie") MovieTransferObject movie, Errors errors) {

        if (errors.hasErrors()) {
            return new ModelAndView("addmovie");
        }

        Movie movieToAdd = new Movie(movie);
        movieService.addMovie(movieToAdd);

        return new ModelAndView("redirect:/admin/addmovie");
    }

    @RequestMapping(value = "/managemovies", method = RequestMethod.GET)
    public ModelAndView manageMovies(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE_AS_STRING) Integer page) {
        if (page < 1) {
            return new ModelAndView("redirect:" + DEFAULT_MOVIES_REDIRECT);
        }

        ModelAndView modelAndView = new ModelAndView("adminmovies");
        PagedEntity pagedMovies = movieService.getAllMoviesLimit((page - 1) * M_RECORDS_PER_PAGE, M_RECORDS_PER_PAGE);
        List<Movie> movies = (List<Movie>) pagedMovies.getEntity();
        List<MovieTransferObject> movieTransferObjects = movies.stream()
                .map(MovieTransferObject::new)
                .collect(Collectors.toList());
        int numberOfRecords = pagedMovies.getNumberOfRecords();
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / M_RECORDS_PER_PAGE);

        modelAndView.addObject("movies", movieTransferObjects);
        modelAndView.addObject("numberOfPages", numberOfPages);
        modelAndView.addObject("currentPage", page);
        return modelAndView;
    }

    @RequestMapping(value = "/managemovies", method = RequestMethod.POST)
    public ModelAndView rating(@RequestParam Long movieId,
                               @RequestParam(defaultValue = DEFAULT_MOVIES_REDIRECT) String redirect) {
        if (movieId > 0 && movieId <= movieService.getMaxMovieId() && movieService.ifMovieExists(movieId)) {
            Movie movieToUpdate = movieService.getMovieByID(movieId);
            List<Review> reviews = reviewService.getReviewsByMovieId(movieId);

            if (reviews.isEmpty()) {
                movieToUpdate.setRating(0d);
                LOGGER.warn("No reviews found for movie " + movieToUpdate.getMovieName() +
                        ". So rating can't be updated. Rating set to 0.");
            } else {
                movieToUpdate.setRating(recountRating(reviews));
            }

            movieService.updateMovie(movieToUpdate);
        }

        return new ModelAndView("redirect:" + redirect);
    }

    @RequestMapping(value = "/managemovies/{movieId}", method = RequestMethod.GET)
    public ModelAndView editMovieView(@PathVariable Long movieId) {

        if (movieId > 0 && movieId <= movieService.getMaxMovieId() && movieService.ifMovieExists(movieId)) {
            ModelAndView modelAndView = new ModelAndView("editmovie");

            MovieContainer movieContainer = completeMovie(movieId);

            modelAndView.addObject("movie", movieContainer.getMovieTransferObject());
            modelAndView.addObject("reviews", movieContainer.getReviews());
            modelAndView.addObject("users", movieContainer.getUsers());
            return modelAndView;
        }

        return new ModelAndView("redirect:" + DEFAULT_MOVIES_REDIRECT);
    }

    @RequestMapping(value = "/managemovies/{movieId}", method = RequestMethod.POST)
    public ModelAndView editMovie(@PathVariable Long movieId,
                                  @Validated @ModelAttribute(value = "movie") MovieTransferObject movie, Errors errors,
                                  @RequestParam(defaultValue = DEFAULT_MOVIES_REDIRECT) String redirect,
                                  RedirectAttributes redirectAttributes) {
        if (movieId < 1 && movieId > movieService.getMaxMovieId() && !movieService.ifMovieExists(movieId)) {
            return new ModelAndView("redirect:" + DEFAULT_MOVIES_REDIRECT);
        }
        if (errors.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("editmovie");
            modelAndView.addObject("reviews", completeMovie(movieId).getReviews());
            modelAndView.addObject("users", completeMovie(movieId).getUsers());
            return modelAndView;
        }

        Movie movieToUpdate = movieService.getMovieByID(movieId);
        movieService.updateMovie(updateMovieFields(movieToUpdate, movie));

        redirectAttributes.addFlashAttribute("success", "Movie updated successfully");
        return new ModelAndView("redirect:" + redirect);
    }

    @RequestMapping(value = "/delreview", method = RequestMethod.POST)
    public ModelAndView deleteReview(@RequestParam Long reviewId,
                                     @RequestParam(defaultValue = DEFAULT_MOVIES_REDIRECT) String redirect) {
        if (reviewId > 0) {
            reviewService.deleteReview(reviewService.getReview(reviewId));// TODO shitty
        }

        return new ModelAndView("redirect:" + redirect);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView users(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE_AS_STRING) Integer page,
                              @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_TYPE) String sortBy,
                              @RequestParam(value = "isDesc", defaultValue = DEFAULT_SORT_DESCENDING) Integer isDesc) {
        // TODO make it adequate
        List<String> types = new ArrayList<>(Arrays.asList("id", "login", "username", "isadmin", "isbanned"));
        if (page < 1 || isDesc < 0 || isDesc > 1 || !(types.stream().anyMatch(sortBy::equals))) {
            return new ModelAndView("redirect:" + DEFAULT_USERS_REDIRECT);
        }

        ModelAndView modelAndView = new ModelAndView("users");
        PagedEntity pagedUsers = userService.getUsersSortedBy(
                (page - 1) * U_RECORDS_PER_PAGE, U_RECORDS_PER_PAGE, sortBy, (1 == isDesc));
        List<User> users = (List<User>) pagedUsers.getEntity();
        int numberOfRecords = pagedUsers.getNumberOfRecords();
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / U_RECORDS_PER_PAGE);

        modelAndView.addObject("sortBy", sortBy);
        modelAndView.addObject("isDesc", isDesc);
        modelAndView.addObject("users", users);
        modelAndView.addObject("numberOfPages", numberOfPages);
        modelAndView.addObject("currentPage", page);
        return modelAndView;
    }

    @RequestMapping(value = "/adminize", method = RequestMethod.POST)
    public ModelAndView adminize(@RequestParam Long userId,
                                 @RequestParam(defaultValue = DEFAULT_USERS_REDIRECT) String redirect) {
        if (userId > 0) {
            Long currentUserId = PrincipalUtil.getCurrentPrincipal().getId();
            User userToUpdate = userService.getUserById(userId);

            if (!currentUserId.equals(userToUpdate.getId())) {
                userToUpdate.setAdmin(!userToUpdate.isAdmin());
                userService.updateUser(userToUpdate);
            } else {
                LOGGER.warn("Can't modify own admin state. User id: " + currentUserId);
            }
        }

        return new ModelAndView("redirect:" + redirect);
    }

    @RequestMapping(value = "/ban", method = RequestMethod.POST)
    public ModelAndView ban(@RequestParam Long userId,
                            @RequestParam(defaultValue = DEFAULT_USERS_REDIRECT) String redirect) {
        if (userId > 0) {
            Long currentUserId = PrincipalUtil.getCurrentPrincipal().getId();
            User userToUpdate = userService.getUserById(userId);

            if (!currentUserId.equals(userToUpdate.getId())) {
                userToUpdate.setBanned(!userToUpdate.isBanned());
                userService.updateUser(userToUpdate);
            } else {
                LOGGER.warn("Can't ban yourself. User id: " + currentUserId);
            }
        }

        return new ModelAndView("redirect:" + redirect);
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.GET)
    public ModelAndView newUser() {
        return new ModelAndView("adminnewuser", "user", new UserTransferObject());
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public ModelAndView addNewUser(@Validated({Default.class, CreateUserValidation.class})
                                   @ModelAttribute(value = "user") UserTransferObject user,
                                   Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return new ModelAndView("adminnewuser");
        }

        if (!userService.ifUserExists(user.getLogin())) {
            String encodedPassword = passwordManager.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userService.createUser(new User(user));
        } else {
            ModelAndView modelAndView = new ModelAndView("adminnewuser");
            modelAndView.addObject("fail", "User with login <b>" + user.getLogin() + "</b> already exists.");
            return modelAndView;
        }

        redirectAttributes.addFlashAttribute("success", "User with username <b>" + user.getName() + "</b> and login <b>"
                + user.getLogin() + "</b> created successfully");
        return new ModelAndView("redirect:/admin/newuser");
    }

    /**
     * Recounts current movie rating based on review ratings
     *
     * @param reviews <code>List</code> of <code>Review</code> objects to be parsed for ratings
     * @return recalculated rating value as <code>Double</code>
     */
    private Double recountRating(List<Review> reviews) {
        Double totalRating = 0d;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }
        DecimalFormat df = new DecimalFormat(DECIMAL_PATTERN);
        Double newRating = totalRating / reviews.size();
        try {
            newRating = Double.valueOf(df.format(newRating));
        } catch (NumberFormatException e) {
            LOGGER.error("Error parsing rating during movie rating update.", e);
        }
        return newRating;
    }

    /**
     * Helper method to get and pack movie data, users and their reviews into one single <code>MovieContainer</code>
     * object for convenience.
     *
     * @param movieId id of movie for witch to retrieve data
     * @return <code>MovieContainer</code> object with all movie-related data
     * @see MovieContainer
     */
    private MovieContainer completeMovie(Long movieId) {
        MovieContainer movieContainer = new MovieContainer();
        Movie movie = movieService.getMovieByID(movieId);
        MovieTransferObject movieTransferObject = new MovieTransferObject(movie);
        List<Review> reviews = reviewService.getReviewsByMovieId(movieId);
        reviews.sort((r1, r2) -> r2.getPostDate().compareTo(r1.getPostDate()));
        Map<Long, Object> users = new HashMap<>();
        if (reviews.size() > 0) {
            for (Review review : reviews) {
                // TODO check this spot. Tricky place
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
     * Populates given movie object with new data from updated movie transfer object
     *
     * @param movieToUpdate movie to be updated
     * @param updatedMovie  updated movie info (movie transfer object)
     * @return <code>Movie</code> object with data populated from updated movie transfer object
     * @see MovieTransferObject
     * @see Movie
     */
    private Movie updateMovieFields(Movie movieToUpdate, MovieTransferObject updatedMovie) {
        movieToUpdate.setMovieName(updatedMovie.getMovieName());
        movieToUpdate.setDirector(updatedMovie.getDirector());
        movieToUpdate.setReleaseDate(updatedMovie.getReleaseDate());
        movieToUpdate.setPosterURL(updatedMovie.getPosterURL());
        movieToUpdate.setTrailerURL(updatedMovie.getTrailerURL());
        movieToUpdate.setDescription(updatedMovie.getDescription());
        return movieToUpdate;
    }

}