package movieappspring.controller;

import movieappspring.entities.Movie;
import movieappspring.entities.PagedEntity;
import movieappspring.entities.User;
import movieappspring.security.PasswordManager;
import movieappspring.security.UserDetailsImpl;
import movieappspring.service.MovieService;
import movieappspring.service.ReviewService;
import movieappspring.service.UserService;
import movieappspring.validation.AdminNewUserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.method.P;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private static final String DEFAULT_PAGE_AS_STRING = "1";
    private static final String DEF_USERS_REDIRECT = "/admin/users";
    private static final Integer RECORDS_PER_PAGE = 10;
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

    @RequestMapping(value = "/adminize", method = RequestMethod.POST)
    public ModelAndView adminize(@RequestParam(value = "userId") Long userId,
                                 @RequestParam(value = "redirect", defaultValue = DEF_USERS_REDIRECT) String redirect) {
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
    public ModelAndView ban(@RequestParam(value = "userId") Long userId,
                            @RequestParam(value = "redirect", defaultValue = DEF_USERS_REDIRECT) String redirect) {
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

    @RequestMapping(value = "/managemovies", method = RequestMethod.GET)
    public ModelAndView manageMovies() {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

    @RequestMapping(value = "/delreview", method = RequestMethod.POST)
    public ModelAndView deleteReview() {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
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

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView users(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE_AS_STRING) Integer page,
                              @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                              @RequestParam(value = "isDesc", defaultValue = "0") String isDesc) {
        ModelAndView modelAndView = new ModelAndView("users");
        if (page < 1) {
            // TODO some logic
        }

        PagedEntity pagedUsers = userService.getUsersSortedBy(
                (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE, sortBy, ("1".equals(isDesc)));
        List<User> users = (List<User>) pagedUsers.getEntity();
        int numberOfRecords = pagedUsers.getNumberOfRecords();
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);

        modelAndView.addObject("sortBy", sortBy);
        modelAndView.addObject("isDesc", isDesc);
        modelAndView.addObject("users", users);
        modelAndView.addObject("numberOfPages", numberOfPages);
        modelAndView.addObject("currentPage", page);
        return modelAndView;
    }

}