package movieappspring.security;

import movieappspring.dao.UserDAO;
import movieappspring.entities.User;
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
    private UserDAO userDAO;

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Attempts to get user details
     *
     * @param s 'username' of user to look for
     * @return <code>UserDetails</code> object with all user specific info (same as in <code>User</code> class)
     * @throws UsernameNotFoundException
     * @see User
     */
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDAO.getByLogin(s);

        if (user == null) {
            LOGGER.warn("User with username " + s + " not found.");
            throw new UsernameNotFoundException("User with username " + s + " not found.");
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
