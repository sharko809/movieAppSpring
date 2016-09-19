package movieappspring.controller;

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
import java.util.LinkedList;

@ControllerAdvice
public class Handler {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView notFound404(HttpServletRequest request) {
        String message = "No such url found on this server \n <b>Requested URL:</b> " +
                request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        return new ModelAndView("error", "errorDetails", message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView methodArgumentMismatch(HttpServletRequest request, Exception exception) {
        System.out.println("bad url: " + request.getRequestURL() +
                (request.getQueryString() == null ? "" : "?" + request.getQueryString()));
        if (request.getQueryString() == null) {
            return new ModelAndView("error", "errorDetails", composeErrorDetails(request, exception));
        }
        return new ModelAndView("redirect:" + request.getRequestURL());
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView error(HttpServletRequest request, Exception exception) {
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