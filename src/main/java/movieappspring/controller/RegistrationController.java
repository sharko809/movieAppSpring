package movieappspring.controller;

import movieappspring.entities.User;
import movieappspring.security.PasswordManager;
import movieappspring.service.UserService;
import movieappspring.validation.RegistrationValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject(new User());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView register(@Validated({RegistrationValidation.class}) @ModelAttribute User user, Errors errors,
                                 RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return new ModelAndView("registration");
        }

        if (!userService.ifUserExists(user.getLogin())) {
            String encodedPassword = passwordManager.encode(user.getPassword());
            userService.createUser(user.getName(), user.getLogin(), encodedPassword, false);
        } else {
            ModelAndView modelAndView = new ModelAndView("registration");
            modelAndView.addObject("fail", "User with login <b>" + user.getLogin() + "</b> already exists.");
            return modelAndView;
        }

        redirectAttributes.addFlashAttribute("success", "User <b>" + user.getName() + "</b> with login <b>" + user.getLogin() +
                "</b> created successfully");
        return new ModelAndView("redirect:/registration");
    }

}
