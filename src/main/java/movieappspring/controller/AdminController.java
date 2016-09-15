package movieappspring.controller;

import movieappspring.entities.Movie;
import movieappspring.entities.PagedEntity;
import movieappspring.entities.Review;
import movieappspring.entities.User;
import movieappspring.security.PasswordManager;
import movieappspring.security.UserDetailsImpl;
import movieappspring.service.MovieService;
import movieappspring.service.ReviewService;
import movieappspring.service.UserService;
import movieappspring.validation.AdminNewUserValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "/addmovie", method = RequestMethod.GET)
    public ModelAndView addMovieView() {
        ModelAndView modelAndView = new ModelAndView("addmovie");
        modelAndView.addObject(new Movie());
        return modelAndView;
    }

    @RequestMapping(value = "/addmovie", method = RequestMethod.POST)
    public ModelAndView addMovie(@Validated @ModelAttribute Movie movie, Errors errors) {
        ModelAndView modelAndView = new ModelAndView();
        if (errors.hasErrors()) {
            return new ModelAndView("addmovie");
        }

        return new ModelAndView("redirect:/admin/addmovie");
    }

    @RequestMapping(value = "/managemovies", method = RequestMethod.GET)
    public ModelAndView manageMovies(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE_AS_STRING) Integer page) {
        ModelAndView modelAndView = new ModelAndView("adminmovies");

        if (page < 1) {
            // TODO some logic
        }

        PagedEntity pagedMovies = movieService.getAllMoviesLimit((page - 1) * M_RECORDS_PER_PAGE, M_RECORDS_PER_PAGE);
        List<Movie> movies = (List<Movie>) pagedMovies.getEntity();
        int numberOfRecords = pagedMovies.getNumberOfRecords();
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / M_RECORDS_PER_PAGE);

        modelAndView.addObject("movies", movies);
        modelAndView.addObject("numberOfPages", numberOfPages);
        modelAndView.addObject("currentPage", page);
        return modelAndView;
    }

    @RequestMapping(value = "/managemovies", method = RequestMethod.POST)
    public ModelAndView rating(@RequestParam Long movieId,
                               @RequestParam(defaultValue = DEFAULT_MOVIES_REDIRECT) String redirect) {
        // TODO handle invalid movie id
        Movie movieToUpdate = movieService.getMovieByID(movieId);
        List<Review> reviews = reviewService.getReviewsByMovieId(movieId);

        if (reviews.isEmpty()) {
            movieToUpdate.setRating(0d);
            LOGGER.warn("No reviews found. So rating can't be updated. Rating set to 0.");
        } else {
            movieToUpdate.setRating(recountRating(reviews));
        }

        movieService.updateMovie(movieToUpdate);
        return new ModelAndView("redirect:" + redirect);
    }

    @RequestMapping(value = "/editmovie", method = RequestMethod.GET)
    public ModelAndView editMovieView() {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

    @RequestMapping(value = "/editmovie", method = RequestMethod.POST)
    public ModelAndView editMovie() {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

    @RequestMapping(value = "/delreview", method = RequestMethod.POST)
    public ModelAndView deleteReview() {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView users(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE_AS_STRING) Integer page,
                              @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                              @RequestParam(value = "isDesc", defaultValue = "0") String isDesc) {
        ModelAndView modelAndView = new ModelAndView("users");
        if (page < 1) {
            // TODO some logic
        }

        PagedEntity pagedUsers = userService.getUsersSortedBy(
                (page - 1) * U_RECORDS_PER_PAGE, U_RECORDS_PER_PAGE, sortBy, ("1".equals(isDesc)));
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
        // TODO validate params
        Long currentUserId =
                ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User userToUpdate = userService.getUserById(userId);

        if (!currentUserId.equals(userToUpdate.getId())) {
            userToUpdate.setAdmin(!userToUpdate.isAdmin());
            userService.updateUser(userToUpdate);
        } else {
            // TODO throw exception?
        }

        return new ModelAndView("redirect:" + redirect);
    }

    @RequestMapping(value = "/ban", method = RequestMethod.POST)
    public ModelAndView ban(@RequestParam Long userId,
                            @RequestParam(defaultValue = DEFAULT_USERS_REDIRECT) String redirect) {
        // TODO validate params
        Long currentUserId =
                ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User userToUpdate = userService.getUserById(userId);

        if (!currentUserId.equals(userToUpdate.getId())) {
            userToUpdate.setBanned(!userToUpdate.isBanned());
            userService.updateUser(userToUpdate);
        } else {
            // TODO throw exception?
        }

        return new ModelAndView("redirect:" + redirect);
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.GET)
    public ModelAndView newUser() {
        ModelAndView modelAndView = new ModelAndView("adminnewuser");
        modelAndView.addObject(new User());
        return modelAndView;
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public ModelAndView addNewUser(@Validated({AdminNewUserValidation.class}) @ModelAttribute User user, Errors errors,
                                   RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return new ModelAndView("adminnewuser");
        }

        if (!userService.ifUserExists(user.getLogin())) {
            String encodedPassword = passwordManager.encode(user.getPassword());
            userService.createUser(user.getName(), user.getLogin(), encodedPassword, user.isAdmin());
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

}