package movieappspring.controller;

import movieappspring.exception.OnGetNullException;
import movieappspring.exception.OnMovieCreateNullException;
import movieappspring.exception.OnReviewCreateNullException;
import movieappspring.exception.OnUserCreateNullException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * Handles case when some serious (database, hibernate) exception occurs during posting new review
     *
     * @param exception exception thrown during posting review error
     * @return view with message correspondent to exception
     */
    @ExceptionHandler(OnReviewCreateNullException.class)
    public ModelAndView createReviewException(OnReviewCreateNullException exception) {
        LOGGER.error("Exception on posting review. " + exception.reviewDetails(), exception);
        List<String> errorDetails = new LinkedList<>(Arrays.asList("Error posting review. \nSome technical issue " +
                "must had happened. \nPlease, try again later.", "Review is not posted"));
        return new ModelAndView("error", "errorDetails", errorDetails);
    }

    /**
     * Handles case when some serious (database, hibernate) exception occurs executing read operations from database
     *
     * @param request   http request
     * @param exception exception thrown during executing read operations from database
     * @return view with message correspondent to exception
     */
    @ExceptionHandler(OnGetNullException.class)
    public ModelAndView onGetException(HttpServletRequest request, Exception exception) {
        LOGGER.error("Exception on querying the database.", exception);
        List<String> errorDetails = composeErrorDetails(request, exception);
        return new ModelAndView("error", "errorDetails", errorDetails);
    }

    /**
     * Handles case when some serious (database, hibernate) exception occurs during creating new movie
     *
     * @param exception exception thrown during creating new movie
     * @return view with message correspondent to exception
     */
    @ExceptionHandler(OnMovieCreateNullException.class)
    public ModelAndView createMovieException(OnMovieCreateNullException exception) {
        LOGGER.error("Exception on adding movie. " + exception.movieDetails(), exception);
        List<String> errorDetails = new LinkedList<>(Arrays.asList("Error adding movie. \nSome technical issue " +
                "must had happened.", "Movie not created"));
        return new ModelAndView("error", "errorDetails", errorDetails);
    }

    /**
     * Handles case when some serious (database, hibernate) exception occurs during creating new user
     *
     * @param exception exception thrown during adding new user
     * @return view with message correspondent to exception
     */
    @ExceptionHandler(OnUserCreateNullException.class)
    public ModelAndView createMovieException(OnUserCreateNullException exception) {
        LOGGER.error("Exception on creating user. " + exception.userDetails(), exception);
        List<String> errorDetails = new LinkedList<>(Arrays.asList("Error creating user. \nSome technical issue " +
                "must had happened.", "User not created"));
        return new ModelAndView("error", "errorDetails", errorDetails);
    }

    /**
     * Handles case when user attempts to access non-existing resource
     *
     * @param request http request
     * @return view with message correspondent to exception. Like "no such url" with provided requested url
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView notFound404(HttpServletRequest request) {
        String message = "No such url found on this server \n <b>Requested URL:</b> " +
                request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        return new ModelAndView("error", "errorDetails", message);
    }

    /**
     * Executed when user attempts to pass invalid parameters in url
     *
     * @param request   http request
     * @param exception exception thrown
     * @return view from which user attempted to get corrupted url, but with adequate url
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView methodArgumentMismatch(HttpServletRequest request, Exception exception) {
        LOGGER.warn("Bad url or argument mismatch: " + request.getRequestURL() +
                (request.getQueryString() == null ? "" : "?" + request.getQueryString()), exception);
        if (request.getQueryString() == null) {
            return new ModelAndView("error", "errorDetails", composeErrorDetails(request, exception));
        }
        return new ModelAndView("redirect:" + request.getRequestURL());
    }

    /**
     * Executes when exception (unhandled above) occurs
     *
     * @param request   http request
     * @param exception thrown exception
     * @return view with message correspondent to exception
     */
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView error(HttpServletRequest request, Exception exception) {
        LOGGER.warn("Exception caught.", exception);
        ModelAndView modelAndView = new ModelAndView("error", "errorDetails", composeErrorDetails(request, exception));
        LOGGER.error("trying to catch: " + exception.getClass());
        LOGGER.error("exception message: " + exception.getLocalizedMessage());
        LOGGER.error("full requested path: " + request.getRequestURL() + "?" + request.getQueryString());
        return modelAndView;
    }

    /**
     * Composes exception details to LinkedList for further rendering on jsp
     *
     * @param request   http request
     * @param exception exception that occurred
     * @return LinkedList with occurred exception details
     */
    private LinkedList<String> composeErrorDetails(HttpServletRequest request, Exception exception) {
        LinkedList<String> errorDetails = new LinkedList<>();
        errorDetails.add("<b>Error message:</b> " + exception.getLocalizedMessage());
        errorDetails.add("<b>Full requested path:</b> " + request.getRequestURL() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
        return errorDetails;
    }

}