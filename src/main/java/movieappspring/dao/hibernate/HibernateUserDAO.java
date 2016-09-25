package movieappspring.dao.hibernate;

import movieappspring.dao.UserDAO;
import movieappspring.entities.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * Class that accesses the database and performs any activity connected with database using hibernate.
 * <p>
 * This particular class deals with user data in database.
 */
@Repository
@Transactional
public class HibernateUserDAO implements UserDAO {

    private static final String SQL_SELECT_ALL = "SELECT SQL_CALC_FOUND_ROWS * FROM user";
    private static final String SQL_SELECT_FOUND_ROWS = "SELECT FOUND_ROWS()";
    private static final String SQL_SELECT_ORDER_BY = "SELECT SQL_CALC_FOUND_ROWS * FROM user ORDER BY ";
    private SessionFactory sessionFactory;
    private Integer numberOfRecords;

    @Autowired
    public HibernateUserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Creates a record for new user in database
     *
     * @param user user to be added to database
     * @return ID (specified by mapping in <code>User</code> class) of created user. If user to some reasons hasn't
     * been created - returns null.
     */
    @Override
    public Long create(User user) {
        return (Long) sessionFactory.getCurrentSession().save(user);
    }

    /**
     * Searches for user with specified login in database
     *
     * @param login login of user to be found
     * @return <code>User</code> object if user with given login is found in database. Otherwise returns null.
     * @see User
     */
    @Override
    public User getByLogin(String login) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE login = :login", User.class)
                .setParameter("login", login)
                .uniqueResult();
    }

    /**
     * Searches for user with specified ID in database
     *
     * @param userID ID of user to be found
     * @return <code>User</code> object if user with given ID exists. Otherwise returns null.
     * @see User
     */
    @Override
    public User get(Long userID) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE id = :id", User.class)
                .setParameter("id", userID)
                .uniqueResult();
    }

    /**
     * Updates user data in database
     *
     * @param user <code>User</code> entity to update
     */
    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    /**
     * Deletes user record from database
     *
     * @param user user to be removed from database
     */
    @Override
    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    /**
     * Returns records for all existing users
     *
     * @return List of <code>User</code> objects if any found. Otherwise returns an empty list
     */
    @Override
    @Deprecated
    public List<User> getAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM User", User.class).list();
    }

    /**
     * Returns some part of users from database.
     *
     * @param offset   starting position of select query
     * @param noOfRows desired number of records per page
     * @return List of <code>User</code>objects in given range if any users found. Otherwise returns empty list
     */
    @Override
    @Deprecated
    public List<User> getAllLimit(Integer offset, Integer noOfRows) {
        List<User> users = sessionFactory.getCurrentSession()
                .createNativeQuery(SQL_SELECT_ALL, User.class)
                .setFirstResult(offset)
                .setMaxResults(noOfRows).list();
        this.numberOfRecords = ((BigInteger) sessionFactory.getCurrentSession()
                .createNativeQuery(SQL_SELECT_FOUND_ROWS)
                .uniqueResult()).intValue();
        return users;
    }

    /**
     * Returns some part of users sorted in particular way from database.
     *
     * @param offset   starting position of select query
     * @param noOfRows desired number of records per page
     * @param orderBy  column by which soring is performed
     * @param isDesc   <b>true</b> if you want descending sorting
     * @return List of <code>User</code> objects in given range if any users found. Otherwise returns empty list
     */
    @Override
    public List<User> getUsersSorted(Integer offset, Integer noOfRows, String orderBy, Boolean isDesc) {
        List<User> users = sessionFactory.getCurrentSession()
                .createNativeQuery(SQL_SELECT_ORDER_BY + orderBy + (isDesc ? " DESC" : " ASC"), User.class)
                .setFirstResult(offset)
                .setMaxResults(noOfRows).list();
        this.numberOfRecords = ((BigInteger) sessionFactory.getCurrentSession()
                .createNativeQuery(SQL_SELECT_FOUND_ROWS)
                .uniqueResult()).intValue();
        return users;
    }

    /**
     * Checks if there is a user record with such login in database.
     *
     * @param login login to check
     * @return <code>true</code> if user record with such login exist. Otherwise returns <code>false</code>
     */
    @Override
    public boolean ifUserExists(String login) {
        String persistedLogin = sessionFactory.getCurrentSession()
                .createQuery("SELECT u.login FROM User u where u.login = :login", String.class)
                .setParameter("login", login)
                .uniqueResult();
        if (persistedLogin != null) {
            if (!persistedLogin.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method used in pagination. Retrieves number of records for current query
     *
     * @return number of records retrieved during last query
     */
    @Override
    public Integer getNumberOfRecords() {
        return this.numberOfRecords;
    }

}