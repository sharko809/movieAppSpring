package movieappspring.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Class for handling strategy when user attempts to access forbidden resource
 */
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        handleDenied(httpServletRequest, httpServletResponse, e);
        clearAuthenticationAttributes(httpServletRequest);
    }

    /**
     * Handles redirect logic
     *
     * @param request   http request
     * @param response  http response
     * @param exception <code>AccessDeniedException</code>
     * @throws ServletException can be thrown during performing forward operation
     * @throws IOException      can be thrown during performing forward operation
     */
    private void handleDenied(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws ServletException, IOException {
        LinkedList<String> errorDetails = new LinkedList<>();
        errorDetails.add("<b>Error message:</b> " + exception.getLocalizedMessage());
        if ("/".equals(request.getServletPath())) {
            errorDetails.add("Authorized users can't access login page");
        }
        request.setAttribute("errorDetails", errorDetails);
        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
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
