package movieappspring.controller;

import movieappspring.PrincipalUtil;
import movieappspring.entities.User;
import movieappspring.entities.dto.UserTransferObject;
import movieappspring.security.PasswordManager;
import movieappspring.security.UserDetailsImpl;
import movieappspring.service.UserService;
import movieappspring.validation.marker.AccountValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.groups.Default;

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
    public ModelAndView account(@RequestParam(value = "id", defaultValue = "0") Long userId) {
        Long currentUserId = PrincipalUtil.getCurrentPrincipal().getId();
        if (userId < 1 || !currentUserId.equals(userId)) {
            return new ModelAndView("redirect:/account?id=" + currentUserId);
        }

        User user = userService.getUserById(userId);
        UserTransferObject userTransferObject = new UserTransferObject();
        userTransferObject.setName(user.getName());
        userTransferObject.setLogin(user.getLogin());
        return new ModelAndView("account", "thisUser", userTransferObject);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView updateAccount(@Validated({Default.class, AccountValidation.class})
                                @ModelAttribute(name = "thisUser") UserTransferObject user, BindingResult errors,
                                RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return new ModelAndView("account");
        }

        Long currentUserId = PrincipalUtil.getCurrentPrincipal().getId();

        if (userService.ifUserExists(user.getLogin())) {
            if (!userService.getUserByLogin(user.getLogin()).getId().equals(currentUserId)) {
                errors.addError(new ObjectError("login", "This login is already in use"));
                return new ModelAndView("account");
            }
        }

        User currentUser = userService.getUserById(currentUserId);
        currentUser.setLogin(user.getLogin());
        currentUser.setName(user.getName());
        if (!user.getPassword().isEmpty()) {
            currentUser.setPassword(passwordManager.encode(user.getPassword()));
        }
        userService.updateUser(currentUser);

        resetAuthentication(currentUser);

        redirectAttributes.addFlashAttribute("success", "Account info updated successfully");
        return new ModelAndView("redirect:/account?id=" + currentUser.getId());
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