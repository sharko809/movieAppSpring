package movieappspring.entities.dto;

import movieappspring.validation.AccountValidation;
import movieappspring.validation.AdminNewUserValidation;
import movieappspring.validation.LoginValidation;
import movieappspring.validation.RegistrationValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Helper class used as DTO for users.
 */
public class UserTransferObject {

    @NotNull(groups = {AccountValidation.class, RegistrationValidation.class, AdminNewUserValidation.class})
    @Size(min = 3, max = 20, message = "{username.size}",
            groups = {AccountValidation.class, RegistrationValidation.class, AdminNewUserValidation.class})
    @Pattern(regexp = "[a-zA-zа-яА-я0-9]+([ '-][a-zA-Zа-яА-Я0-9]+)*", message = "{username.pattern}",
            groups = {AccountValidation.class, RegistrationValidation.class, AdminNewUserValidation.class})
    private String name;

    @NotNull(groups = {AccountValidation.class, LoginValidation.class, RegistrationValidation.class, AdminNewUserValidation.class})
    @Size(min = 3, max = 60, message = "{login.size}",
            groups = {AccountValidation.class, LoginValidation.class, RegistrationValidation.class, AdminNewUserValidation.class})
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "{login.pattern}",
            groups = {AccountValidation.class, LoginValidation.class, RegistrationValidation.class, AdminNewUserValidation.class})
    private String login;

    @NotNull(groups = {LoginValidation.class, RegistrationValidation.class, AdminNewUserValidation.class})
    @Size(min = 3, max = 15, message = "{password.size}",
            groups = {LoginValidation.class, RegistrationValidation.class, AdminNewUserValidation.class})
    private String password;

    private Boolean admin;

    public UserTransferObject() {
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}