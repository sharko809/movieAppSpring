package movieappspring.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class implements <code>AuthenticationFailureHandler</code>. Single public overridden method
 * <code>onAuthenticationFailure</code> determines strategy to use depending on occurred exceptions
 */
public class AuthFailureHandler implements AuthenticationFailureHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        handle(httpServletRequest, httpServletResponse, e);
        clearAuthenticationAttributes(httpServletRequest);
    }

    /**
     * Builds action strategy based on user inputs or exceptions thrown
     *
     * @param request   http request
     * @param response  http response
     * @param exception thrown exception
     * @throws ServletException can be thrown during performing forward operation
     * @throws IOException      can be thrown during performing forward operation
     */
    private void handle(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        LOGGER.info("Login: " + login + ", password: " + password);

        List<String> errors = new ArrayList<>(validate(login, password));

        if (response.isCommitted()) {
            LOGGER.debug("Response has been committed. Can't redirect");
            return;
        }

        if (!errors.isEmpty()) {
            request.setAttribute("result", errors);
            request.setAttribute("login", login);
            request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
        } else if (exception instanceof BadCredentialsException) {
            request.setAttribute("result", "Wrong password or username");
            request.setAttribute("login", login);
            request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
        } else if (exception.getLocalizedMessage().startsWith("Banned user ")) {// TODO probably not the best idea
            request.setAttribute("result", exception.getLocalizedMessage());
            request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
        } else {
            LinkedList<String> errorDetails = new LinkedList<>();
            errorDetails.add("<b>Error message:</b> " + exception.getLocalizedMessage());
            request.setAttribute("errorDetails", errorDetails);
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }

    }

    /**
     * Validates user login and password by pattern and length
     *
     * @param login    user login to validate
     * @param password user password to validate
     * @return <code>List</code> with <code>String</code>'s representing errors if any found. Otherwise returns
     * empty list.
     */
    private List<String> validate(String login, String password) {
        String emailPattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher;
        List<String> errors = new ArrayList<>();

        if (login != null) {
            matcher = pattern.matcher(login);
            if (!matcher.matches()) {
                errors.add("Please, enter a valid email");
            }
        } else {
            errors.add("Please, enter a valid email");
        }

        if (password != null) {
            if (password.isEmpty() || password.length() < 3) {
                errors.add("Password should not be empty and must have at least " + 3 + " characters");
            }
        } else {
            errors.add("Password should not be empty and must have at least " + 3 + " characters");
        }

        return errors;
    }

    /**
     * Removes authentication exceptions from session if exists.
     *
     * @param request HttpServletRequest
     */
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
