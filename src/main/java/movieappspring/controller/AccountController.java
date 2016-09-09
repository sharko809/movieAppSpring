package movieappspring.controller;

import movieappspring.entities.User;
import movieappspring.security.PasswordManager;
import movieappspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
    public String updateAccount(User user) {
        User currentUser = userService.getUserById(user.getId());
        currentUser.setLogin(user.getLogin());
        currentUser.setName(user.getName());
        if (!user.getPassword().isEmpty()) {
            currentUser.setPassword(passwordManager.encode(user.getPassword()));
        }
        userService.updateUser(currentUser);
        // TODO update current principal
        return "redirect:/account?id=" + currentUser.getId();
    }

}
