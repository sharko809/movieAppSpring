package movieappspring.dao;

import movieappspring.entities.User;

import java.util.List;

/**
 * Interface declaring witch methods should be used in each particular User DAO implementation
 */
public interface UserDAO {

    Long create(String userName, String login, String password, Boolean isAdmin);

    User getByLogin(String login);

    User get(Long userID);

    void update(User user);

    boolean delete(Long userID);

    List<User> getAll();

    List<User> getAllLimit(Integer offset, Integer noOfRows);

    List<User> getUsersSorted(Integer offset, Integer noOfRows, String orderBy, Boolean isDesc);

    boolean ifUserExists(String login);

    Integer getNumberOfRecords();

}