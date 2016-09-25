package movieappspring.exception;

import movieappspring.entities.User;

/**
 * This exception is thrown when SQL, Hibernate or Database exception occur during writing (adding user) to database
 */
public class OnUserCreateNullException extends Exception {

    private User user;

    public OnUserCreateNullException(String message, User user) {
        super(message);
        this.user = user;
    }

    public String userDetails() {
        return "User{" +
                "id=" + user.getId() +
                ", name='" + user.getName() + '\'' +
                ", login='" + user.getLogin() + '\'' +
                ", admin=" + user.getAdmin() +
                ", banned=" + user.isBanned() +
                '}';
    }

}
