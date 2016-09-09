package movieappspring.service;

import movieappspring.dao.UserDAO;
import movieappspring.entities.PagedEntity;
import movieappspring.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Class containing all methods to interact with users
 */
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger();
    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Creates new user with given details.
     *
     * @param userName users nickname that will be displayed to other users
     * @param login    user login used to log in the system. Not visible to other common users
     * @param password user password (should be in encoded form)
     * @param isAdmin  use <b>true</b> if you want to grant user admin rights
     * @return ID of created user.
     */
    public Long createUser(String userName, String login, String password, Boolean isAdmin) {
        Long userId = userDAO.create(userName, login, password, isAdmin);

        if (userId == null || userId == 0) {
            LOGGER.error("Unable to create user. DAO method returned " + userId);
            // TODO create exception
        }

        return userId;
    }

    /**
     * Get user with specified login
     *
     * @param login login of user to be found
     * @return <code>User</code> object if user with given login exists. Otherwise returns null.
     * @see User
     */
    public User getUserByLogin(String login) {
        User user = userDAO.getByLogin(login);
        if (user == null) {
            LOGGER.error("Unable to get user by login " + login + ". DAO method returned null");
            // TODO create exception
        }
        return user;
    }

    /**
     * Searches for user with specified ID in database
     *
     * @param userId ID of user to be found
     * @return <code>User</code> object if user with given ID exists. Otherwise returns null.
     * @see User
     */
    public User getUserById(Long userId) {
        User user = userDAO.get(userId);
        if (user == null) {
            LOGGER.error("Unable to get user by id " + userId + ". DAO method returned null");
            // TODO create exception
        }
        return user;
    }

    /**
     * Updates user data in database
     *
     * @param user <code>User</code> entity to update
     */
    public void updateUser(User user) {
        userDAO.update(user);
    }

    /**
     * Deletes user
     *
     * @param userId ID of user to be removed from database
     * @return <b>true</b> if user has been successfully deleted. Otherwise returns <b>false</b>
     */
    public boolean deleteUser(Long userId) {
        return userDAO.delete(userId);
    }

    /**
     * Returns records for all existing users
     *
     * @return List of <code>User</code> objects if any found. Otherwise returns an empty list
     * @see User
     */
    public List<User> getAllUsers() {
        List<User> users = userDAO.getAll();
        if (users == null) {
            LOGGER.error("Unable to get all users. DAO method returned null");
            // TODO create exception
        }
        return users;
    }

    /**
     * Returns some part of existing users.
     *
     * @param offset   starting position of select query
     * @param noOfRows desired number of users
     * @return <code>PagedEntity</code> object storing List of <code>User</code> objects in given range and
     * Number of Records if any users found. Otherwise returns <code>PagedEntity</code> object with
     * empty list and null records value.
     * @see PagedEntity
     */
    public PagedEntity getUsersWithLimit(Integer offset, Integer noOfRows) {
        List<User> users = userDAO.getAllLimit(offset, noOfRows);
        Integer numberOfRecords = userDAO.getNumberOfRecords();
        if (users == null || numberOfRecords == null) {
            LOGGER.error("Unable to get users for offset " + offset + " and number of users " + noOfRows +
                    ". DAO method returned " + users + " users and " + numberOfRecords + " number of records");
            // TODO create exception
        }
        PagedEntity pagedUsers = new PagedEntity();
        pagedUsers.setEntity(users);
        pagedUsers.setNumberOfRecords(numberOfRecords);
        return pagedUsers;
    }

    /**
     * Returns some part of existing users sorted in particular way.
     *
     * @param offset       starting position of select query
     * @param noOfRows     desired number of records per page
     * @param orderBy      column by which soring is performed
     * @param isDescending <b>true</b> if you want descending sorting
     * @return <code>PagedEntity</code> object storing List of <code>User</code> objects in given range and sorted in
     * particular way and Number of Records if any users found. Otherwise returns <code>PagedEntity</code> object with
     * empty list and null records value.
     * @see PagedEntity
     */
    public PagedEntity getUsersSortedBy(Integer offset, Integer noOfRows, String orderBy, Boolean isDescending) {
        List<User> users = userDAO.getUsersSorted(offset, noOfRows, orderBy, isDescending);
        Integer numberOfRecords = userDAO.getNumberOfRecords();
        if (users == null || numberOfRecords == null) {
            LOGGER.error("Unable to get users for offset " + offset + ", number of users " + noOfRows +
                    " and sorted by " + orderBy + ". DAO method returned " + users + " users and " + numberOfRecords
                    + " number of records");
            // TODO create exception
        }
        PagedEntity pagedUsers = new PagedEntity();
        pagedUsers.setEntity(users);
        pagedUsers.setNumberOfRecords(numberOfRecords);
        return pagedUsers;
    }

}