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
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @ExceptionHandler(OnReviewCreateNullException.class)
    public ModelAndView createReviewException(OnReviewCreateNullException exception) {
        LOGGER.error("Exception on posting review. " + exception.reviewDetails(), exception);
        List<String> errorDetails = new LinkedList<>(Arrays.asList("Error posting review. \nSome technical issue " +
                "must had happened. \nPlease, try again later.", "Review is not posted"));
        return new ModelAndView("error", "errorDetails", errorDetails);
    }

    @ExceptionHandler(OnGetNullException.class)
    public ModelAndView onGetException(HttpServletRequest request, Exception exception) {
        LOGGER.error("Exception on querying the database.", exception);
        List<String> errorDetails = composeErrorDetails(request, exception);
        return new ModelAndView("error", "errorDetails", errorDetails);
    }

    @ExceptionHandler(OnMovieCreateNullException.class)
    public ModelAndView createMovieException(OnMovieCreateNullException exception) {
        LOGGER.error("Exception on adding movie. " + exception.movieDetails(), exception);
        List<String> errorDetails = new LinkedList<>(Arrays.asList("Error adding movie. \nSome technical issue " +
                "must had happened.", "Movie not created"));
        return new ModelAndView("error", "errorDetails", errorDetails);
    }

    @ExceptionHandler(OnUserCreateNullException.class)
    public ModelAndView createMovieException(OnUserCreateNullException exception) {
        LOGGER.error("Exception on creating user. " + exception.userDetails(), exception);
        List<String> errorDetails = new LinkedList<>(Arrays.asList("Error creating user. \nSome technical issue " +
                "must had happened.", "User not created"));
        return new ModelAndView("error", "errorDetails", errorDetails);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView notFound404(HttpServletRequest request) {
        String message = "No such url found on this server \n <b>Requested URL:</b> " +
                request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        return new ModelAndView("error", "errorDetails", message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView methodArgumentMismatch(HttpServletRequest request, Exception exception) {
        LOGGER.warn("Bad url or argument mismatch: " + request.getRequestURL() +
                (request.getQueryString() == null ? "" : "?" + request.getQueryString()), exception);
        if (request.getQueryString() == null) {
            return new ModelAndView("error", "errorDetails", composeErrorDetails(request, exception));
        }
        return new ModelAndView("redirect:" + request.getRequestURL());
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView error(HttpServletRequest request, Exception exception) {
        LOGGER.warn("Exception caught.", exception);
        ModelAndView modelAndView = new ModelAndView("error", "errorDetails", composeErrorDetails(request, exception));
        System.out.println("trying to catch: " + exception.getClass());
        System.out.println("exception message: " + exception.getLocalizedMessage());
        System.out.println("full requested path: " + request.getRequestURL() + "?" + request.getQueryString());
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