package movieappspring.controller;

import movieappspring.entities.User;
import movieappspring.entities.dto.UserTransferObject;
import movieappspring.security.PasswordManager;
import movieappspring.service.UserService;
import movieappspring.validation.marker.RegistrationValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.groups.Default;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    private UserService userService;
    private PasswordManager passwordManager;

    @Autowired
    public RegistrationController(UserService userService, PasswordManager passwordManager) {
        this.userService = userService;
        this.passwordManager = passwordManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView registrationView() {
        return new ModelAndView("registration", "user", new UserTransferObject());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView register(@Validated({Default.class, RegistrationValidation.class})
                                 @ModelAttribute(value = "user") UserTransferObject user,
                                 Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return new ModelAndView("registration");
        }

        if (!userService.ifUserExists(user.getLogin())) {
            String encodedPassword = passwordManager.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setAdmin(false);
            userService.createUser(new User(user));
        } else {
            ModelAndView modelAndView = new ModelAndView("registration");
            modelAndView.addObject("fail", "User with login <b>" + user.getLogin() + "</b> already exists.");
            return modelAndView;
        }

        redirectAttributes.addFlashAttribute("success", "User <b>" + user.getName() + "</b> with login <b>" +
                user.getLogin() + "</b> created successfully");
        return new ModelAndView("redirect:/registration");
    }

}