package movieappspring.controller;

import movieappspring.entities.User;
import movieappspring.security.PasswordManager;
import movieappspring.security.UserDetailsImpl;
import movieappspring.service.UserService;
import movieappspring.validation.AccountValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private PasswordManager passwordManager;
    private UserService userService;

    @Autowired
    public AccountController(UserService userService, PasswordManager passwordManager) {
        this.userService = userService;
        this.passwordManager = passwordManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView account(@RequestParam(value = "id") Long userId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("account");

        if (userId <= 0) {
            // TODO handle + foreign acct handler
        }

        User user = userService.getUserById(userId);
        modelAndView.addObject("thisUser", user);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String updateAccount(@Validated({AccountValidation.class}) @ModelAttribute(name = "thisUser") User user, Errors errors) {
        if (errors.hasErrors()) {
            return "account";
        }
        User currentUser = userService.getUserById(user.getId());
        currentUser.setLogin(user.getLogin());
        currentUser.setName(user.getName());
        if (!user.getPassword().isEmpty()) {
            currentUser.setPassword(passwordManager.encode(user.getPassword()));
        }
        userService.updateUser(currentUser);

        resetAuthentication(currentUser);

        return "redirect:/account?id=" + currentUser.getId();
    }

    /**
     * Replaces current Authentication in SecurityContext with the new one - updates user details.
     *
     * @param currentUser user to be populated into Authentication
     */
    private void resetAuthentication(User currentUser) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailsImpl(currentUser.getId(), currentUser.getName(), currentUser.getLogin(),
                        currentUser.getPassword(), authentication.getAuthorities(), currentUser.isBanned()),
                currentUser.getPassword(), authentication.getAuthorities()));
    }

}