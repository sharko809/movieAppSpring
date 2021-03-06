package movieappspring.service;

import movieappspring.dao.UserDAO;
import movieappspring.entities.User;
import movieappspring.entities.util.PagedEntity;
import movieappspring.exception.OnGetNullException;
import movieappspring.exception.OnUserCreateNullException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Class containing all methods to interact with users
 */
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger();
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Creates new user with given details.
     *
     * @param user user to be added to database
     * @return ID of created user.
     */
    public Long createUser(User user) throws OnUserCreateNullException {
        Long userId = userDAO.create(user);

        if (userId == null) {
            LOGGER.error("Unable to create user. DAO method returned null.");
            throw new OnUserCreateNullException("Unable to create user.", user);
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
    public User getUserByLogin(String login) throws OnGetNullException {
        User user = userDAO.getByLogin(login);
        if (user == null) {
            LOGGER.error("Unable to get user by login " + login + ". DAO method returned null");
            throw new OnGetNullException("Unable to get user by login " + login + ".");
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
    public User getUserById(Long userId) throws OnGetNullException {
        User user = userDAO.get(userId);
        if (user == null) {
            LOGGER.error("Unable to get user by id " + userId + ". DAO method returned null");
            throw new OnGetNullException("Unable to get user by id " + userId + ".");
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
     * @param user user to be removed from database
     */
    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    /**
     * Returns records for all existing users
     *
     * @return List of <code>User</code> objects if any found. Otherwise returns an empty list
     * @see User
     */
    @Deprecated
    public List<User> getAllUsers() throws OnGetNullException {
        List<User> users = userDAO.getAll();
        if (users == null) {
            LOGGER.error("Unable to get all users. DAO method returned null");
            throw new OnGetNullException("Unable to get all users.");
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
    @Deprecated
    public PagedEntity getUsersWithLimit(Integer offset, Integer noOfRows) throws OnGetNullException {
        List<User> users = userDAO.getAllLimit(offset, noOfRows);
        Integer numberOfRecords = userDAO.getNumberOfRecords();
        if (users == null || numberOfRecords == null) {
            LOGGER.error("Unable to get users for offset " + offset + " and number of users " + noOfRows +
                    ". DAO method returned " + users + " users and " + numberOfRecords + " number of records");
            throw new OnGetNullException("Unable to get users.");
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
    public PagedEntity getUsersSortedBy(Integer offset, Integer noOfRows, String orderBy, Boolean isDescending)
            throws OnGetNullException {
        List<User> users = userDAO.getUsersSorted(offset, noOfRows, orderBy, isDescending);
        Integer numberOfRecords = userDAO.getNumberOfRecords();
        if (users == null || numberOfRecords == null) {
            LOGGER.error("Unable to get users for offset " + offset + ", number of users " + noOfRows +
                    " and sorted by " + orderBy + ". DAO method returned " + users + " users and " + numberOfRecords
                    + " number of records");
            throw new OnGetNullException("Unable to sort users.");
        }
        PagedEntity pagedUsers = new PagedEntity();
        pagedUsers.setEntity(users);
        pagedUsers.setNumberOfRecords(numberOfRecords);
        return pagedUsers;
    }

    public boolean ifUserExists(String login) {
        return userDAO.ifUserExists(login);
    }

}