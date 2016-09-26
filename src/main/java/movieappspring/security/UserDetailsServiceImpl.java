package movieappspring.security;

import movieappspring.entities.User;
import movieappspring.exception.OnGetNullException;
import movieappspring.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom authentication provider for Spring Security to authenticate users
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LogManager.getLogger();
    private UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * Attempts to get user details
     *
     * @param login login of user to look for
     * @return <code>UserDetails</code> object with all user specific info (same as in <code>User</code> class)
     * @throws UsernameNotFoundException thrown when user with provided login is not found in database
     * @see User
     */
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        LOGGER.info("LOGIN: " + login);
        User user;
        try {
            user = userService.getUserByLogin(login);
        } catch (OnGetNullException e) {
            LOGGER.error("Error during authorizing user", e);
            throw new UsernameNotFoundException("Error during authorizing user. User not found.");
        }

        if (user == null) {
            LOGGER.warn("User with login " + login + " not found.");
            throw new UsernameNotFoundException("User with login " + login + " not found.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (!user.isAdmin()) {
            LOGGER.info("Not admin auth attempt");
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            LOGGER.info("Admin auth attempt");
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new UserDetailsImpl(
                user.getId(), user.getName(), user.getLogin(), user.getPassword(), authorities, user.isBanned());
    }

}