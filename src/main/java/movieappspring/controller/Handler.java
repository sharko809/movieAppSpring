package movieappspring.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.SimpleDateFormat;

@ControllerAdvice
public class Handler {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such url on this server, sorry")
    @ExceptionHandler(NoHandlerFoundException.class)
    public void notFound(HttpServletRequest request, Exception ex, HttpServletResponse response) {
        System.out.println("Exception class: " + ex.getClass());
        System.out.println("Exception message: " + ex.getLocalizedMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView methodArgumentMismatch(HttpServletRequest request) {
        System.out.println("bad url: " + request.getRequestURL() + (request.getQueryString() == null ? "" : "?" + request.getQueryString()));
        return new ModelAndView("redirect:" + request.getRequestURL());
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView error(HttpServletRequest request, Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("trying to catch: " + exception.getClass());
        System.out.println("exception message: " + exception.getLocalizedMessage());
        System.out.println("full requested path: " + request.getRequestURL() + "?" + request.getQueryString());
        return modelAndView;
    }


}
