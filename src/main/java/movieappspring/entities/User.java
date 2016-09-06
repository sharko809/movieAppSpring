package movieappspring.entities;

/**
 * Class representing entity <b>User</b>
 */
public class User {

    /**
     * User id from database
     */
    private Long id;

    /**
     * Username used to display in UI
     */
    private String name;

    /**
     * Login is used to log in the service.
     * Only visible to admin.
     */
    private String login;

    /**
     * User password to access service.
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (admin != null ? !admin.equals(user.admin) : user.admin != null) return false;
        return banned != null ? banned.equals(user.banned) : user.banned == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (admin != null ? admin.hashCode() : 0);
        result = 31 * result + (banned != null ? banned.hashCode() : 0);
        return result;
    }
}
