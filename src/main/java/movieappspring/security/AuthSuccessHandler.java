package movieappspring.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class implements <code>AuthenticationSuccessHandler</code>. Single public overridden method
 * <code>onAuthenticationSuccess</code> determines URL which user will be redirected to after successful
 * authentication.
 */
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = LogManager.getLogger();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        handle(httpServletRequest, httpServletResponse, authentication);
        clearAuthenticationAttributes(httpServletRequest);
    }

    /**
     * Handles redirect strategy based upon user role.
     *
     * @param request        HttpServletRequest
     * @param response       HttpServletResponse
     * @param authentication user auth details
     */
    private void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        if (((UserDetailsImpl) authentication.getPrincipal()).isBanned()) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            request.setAttribute("result", "You are banned");
            request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
            return;
        }

        String redirectURL = request.getParameter("redirect");
        String targetURL;

        if (redirectURL == null) {
            targetURL = makeTargetUrl(authentication);
        } else {
            targetURL = redirectURL;
        }

        if (response.isCommitted()) {
            LOGGER.debug("Response has been committed. Can't redirect to " + targetURL);
            return;
        }

        try {
            redirectStrategy.sendRedirect(request, response, targetURL);
        } catch (IOException e) {
            LOGGER.error("Exception during redirect to: " + targetURL, e);
            try {
                redirectStrategy.sendRedirect(request, response, targetURL);
            } catch (IOException e1) {
                LOGGER.error("Exception during redirect to: " + targetURL, e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Build target URL depending on user role.
     *
     * @param authentication user auth details
     * @return String redirect URL based on user role
     */
    private String makeTargetUrl(Authentication authentication) {
        boolean user = false;
        boolean admin = false;

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                admin = true;
                break;
            } else if ("ROLE_USER".equals(authority.getAuthority())) {
                user = true;
                break;
            }
        }

        if (user) {
            return "/movies";
        } else if (admin) {
            return "/admin";
        } else {
            LOGGER.warn("User with no role detected. " + authentication.getCredentials());
            throw new IllegalStateException("User with no role detected. " + authentication.getCredentials());
        }
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
