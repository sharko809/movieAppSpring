package movieappspring.entities;

import movieappspring.validation.AccountValidation;
import movieappspring.validation.LoginValidation;
import movieappspring.validation.RegistrationValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Class representing <code>User</code> entity.
 */
public class User {

    /**
     * User id from database
     */
    @NotNull
    private Long id;

    /**
     * Username used to display in UI
     */
    @NotNull(groups = {AccountValidation.class, RegistrationValidation.class})
    @Size(min = 3, max = 20, message = "{username.size}",
            groups = {AccountValidation.class, RegistrationValidation.class})
    @Pattern(regexp = "[a-zA-zа-яА-я0-9]+([ '-][a-zA-Zа-яА-Я0-9]+)*", message = "{username.pattern}",
            groups = {AccountValidation.class, RegistrationValidation.class})
    private String name;

    /**
     * Login is used to log in the service.
     * Only visible to admin.
     */
    @NotNull(groups = {AccountValidation.class, LoginValidation.class, RegistrationValidation.class})
    @Size(min = 3, max = 60, message = "{login.size}",
            groups = {AccountValidation.class, LoginValidation.class, RegistrationValidation.class})
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "{login.pattern}",
            groups = {AccountValidation.class, LoginValidation.class, RegistrationValidation.class})
    private String login;

    /**
     * User password to access service.
     */
    @NotNull(groups = {LoginValidation.class, RegistrationValidation.class})
    @Size(min = 3, max = 15, message = "{password.size}",
            groups = {LoginValidation.class, RegistrationValidation.class})
    private String password;

    /**
     * Field indicating if user has admin rights.
     * <b>true</b> - user has admin rights
     */
    private Boolean admin;

    /**
     * Field indicating if user is banned.
     * Banned users can't access service functions.
     * <b>true</b> - user is banned
     */
    private Boolean banned;

    public User() {
    }

    public Boolean isBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}