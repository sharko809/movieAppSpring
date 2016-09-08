package movieappspring.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param authentication user auth details
     */
    private void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                        Authentication authentication) {
        String targetURL = makeTargetUrl(authentication);

        if (httpServletResponse.isCommitted()) {
            LOGGER.debug("Response has been committed. Can't redirect to " + targetURL);
            return;
        }

        try {
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetURL);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO handle (or?..)
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
            return "/home";
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
