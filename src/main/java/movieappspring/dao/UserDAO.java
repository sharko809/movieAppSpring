package movieappspring.dao;

import movieappspring.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that accesses the database and performs any activity connected with database.
 * <p>
 * This particular class deals with user data in database.
 */
public class UserDAO {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Query for counting rows found by queries
     */
    private static final String COUNT_FOUND_ROWS = "SELECT FOUND_ROWS()";

    /**
     * Query for adding new record with user data to database
     */
    private static final String SQL_CREATE_USER = "INSERT INTO USER (username, login, password, isadmin) " +
            "VALUES (?, ?, ?, ?)";

    /**
     * Query for selecting user with given ID from database
     */
    private static final String SQL_GET_USER = "SELECT * FROM USER WHERE ID = ?";

    /**
     * Query for removing user with given ID from database
     */
    private static final String SQL_DELETE_USER = "DELETE FROM USER WHERE ID = ?";

    /**
     * Query for retrieving all users records from database
     */
    private static final String SQL_GET_ALL_USERS = "SELECT * FROM USER";

    /**
     * Query for updating user record in database
     */
    private static final String SQL_UPDATE_USER = "UPDATE USER SET " +
            "username = ?, login = ?, password = ?, isadmin = ?, isbanned = ? WHERE ID = ?";

    /**
     * Query for retrieving user with specified login from database
     */
    private static final String SQL_GET_USER_BY_NAME = "SELECT * FROM USER WHERE login = ?";

    /**
     * Query for retrieving part of user records specified by "LIMIT"
     */
    private static final String SQL_GET_ALL_USERS_WITH_LIMIT = "SELECT SQL_CALC_FOUND_ROWS * FROM USER LIMIT ?, ?";

    /**
     * Query for retrieving part of user records specified by "LIMIT" and ordered in desired way
     */
    private static final String SQL_GET_USERS_SORTED_BY = "SELECT SQL_CALC_FOUND_ROWS * FROM USER ORDER BY @ LIMIT ?, ?";

    private Integer numberOfRecords;

    private ConnectionManager connectionManager;

    @Autowired
    public UserDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Helper method for creating proper query string
     *
     * @param query   query itself
     * @param orderBy column to order
     * @param isDesc  is sorting descending
     * @return properly formatted query string
     */
    private static String makeSortQuery(String query, String orderBy, Boolean isDesc) {
        return query.replace("@", isDesc ? orderBy + " DESC " : orderBy);
    }

    /**
     * Helper method to parse result set
     *
     * @param resultSet result set to parse
     * @return User object with filled fields
     */
    private static User parseUserResultSet(ResultSet resultSet) {
        User user = new User();
        try {
            user.setId(resultSet.getLong("ID"));
            user.setName(resultSet.getString("username"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setAdmin(resultSet.getBoolean("isadmin"));
            user.setBanned(resultSet.getBoolean("isbanned"));
        } catch (SQLException e) {
            LOGGER.error("Error parsing user result set.", e);
            return null;
        }
        return user;
    }

    /**
     * Creates a record for new user in database
     *
     * @param userName users nickname that will be displayed to other users
     * @param login    user login used to log in the system. Not visible to other common users
     * @param password user password in encoded form
     * @param isAdmin  use <b>true</b> if you want to grant user admin rights
     * @return ID of created user. If user to some reasons hasn't been created - returns 0.
     */
    public Long create(String userName, String login, String password, Boolean isAdmin) {
        Long userID = 0L;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, userName);
            statement.setString(2, login);
            statement.setString(3, password);
            statement.setBoolean(4, isAdmin);
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    userID = resultSet.getLong(1);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error creating new user record.", e);
            return null;
        }
        return userID;
    }

    /**
     * Searches for user with specified login in database
     *
     * @param login login of user to be found
     * @return User entity object if user with given login is found in database. Otherwise returns null.
     */
    public User getByLogin(String login) {
        User user;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_USER_BY_NAME)) {

            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = parseUserResultSet(resultSet);
                } else {
                    user = null;
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error retrieving user by login.", e);
            return null;
        }
        return user;
    }

    /**
     * Searches for user with specified ID in database
     *
     * @param userID ID of user to be found
     * @return User entity object if user with given ID is found in database. Otherwise returns null.
     */
    public User get(Long userID) {
        User user = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_USER)) {

            statement.setLong(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = parseUserResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error retrieving user from database.", e);
            return null;
        }
        return user;
    }

    /**
     * Updates user data in database
     *
     * @param user user entity to update
     */
    public void update(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setBoolean(4, user.isAdmin());
            statement.setBoolean(5, user.isBanned());
            statement.setLong(6, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Error updating user " + user.getLogin() + " ID: " + user.getId(), e);
        }
    }

    /**
     * Deletes user record from database
     *
     * @param userID ID of user to be removed from database
     * @return <b>true</b> if user has been successfully deleted. Otherwise returns <b>false</b>
     */
    public boolean delete(Long userID) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER)) {

            statement.setLong(1, userID);
            int afterUpdate = statement.executeUpdate();
            if (afterUpdate >= 1) {
                return true;
            }

        } catch (SQLException e) {
            LOGGER.error("Error removing user record from database.", e);
            return false;
        }
        return false;
    }

    /**
     * Returns records for all users in database
     *
     * @return List of User objects if any found. Otherwise returns an empty list
     */
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_USERS)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user;
                    user = parseUserResultSet(resultSet);
                    users.add(user);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error retrieving all users from database.", e);
            return null;
        }
        return users;
    }

    /**
     * Returns some part of users from database.
     *
     * @param offset   starting position of select query
     * @param noOfRows desired number of records per page
     * @return List of User objects in given range if any users found. Otherwise returns empty list
     */
    public List<User> getAllLimit(Integer offset, Integer noOfRows) {
        List<User> users = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_USERS_WITH_LIMIT)) {

            statement.setInt(1, offset);
            statement.setInt(2, noOfRows);
            ResultSet resultSet = null;
            try {
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    User user;
                    user = parseUserResultSet(resultSet);
                    users.add(user);
                }
                resultSet.close();
                resultSet = statement.executeQuery(COUNT_FOUND_ROWS);
                if (resultSet.next()) {
                    this.numberOfRecords = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                throw new SQLException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error retrieving users list with offset " + offset +
                    " and number of records " + noOfRows + ". ", e);
            return null;
        }
        return users;
    }

    /**
     * Returns some part of users sorted in particular way from database.
     *
     * @param offset   starting position of select query
     * @param noOfRows desired number of records per page
     * @param orderBy  column by which soring is performed
     * @param isDesc   <b>true</b> if you want descending sorting
     * @return List of User objects in given range if any users found. Otherwise returns empty list
     */
    public List<User> getUsersSorted(Integer offset, Integer noOfRows, String orderBy, Boolean isDesc) {
        List<User> users = new ArrayList<>();
        String query = makeSortQuery(SQL_GET_USERS_SORTED_BY, orderBy, isDesc);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, offset);
            statement.setInt(2, noOfRows);
            ResultSet resultSet = null;
            try {
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    User user;
                    user = parseUserResultSet(resultSet);
                    users.add(user);
                }
                resultSet.close();
                resultSet = statement.executeQuery(COUNT_FOUND_ROWS);
                if (resultSet.next()) {
                    this.numberOfRecords = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                throw new SQLException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error retrieving sorted users list with offset " + offset +
                    " and number of records " + noOfRows + ". ", e);
            return null;
        }
        return users;
    }

    /**
     * Method used in pagination. Retrieves number of records for current query
     *
     * @return number of records retrieved during last query
     */
    public Integer getNumberOfRecords() {
        return this.numberOfRecords;
    }

}
