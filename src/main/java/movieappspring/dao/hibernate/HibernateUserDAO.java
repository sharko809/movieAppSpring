package movieappspring.dao.hibernate;

import movieappspring.dao.UserDAO;
import movieappspring.entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class that accesses the database and performs any activity connected with database using hibernate.
 * <p>
 * This particular class deals with user data in database.
 */
@Repository
@Transactional
public class HibernateUserDAO implements UserDAO {

    private SessionFactory sessionFactory;
    private Integer numberOfRecords;

    @Autowired
    public HibernateUserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long create(String userName, String login, String password, Boolean isAdmin) {
        User user = new User();
        user.setName(userName);
        user.setLogin(login);
        user.setPassword(password);
        user.setAdmin(isAdmin);
        user.setBanned(false);
        sessionFactory.getCurrentSession().save(user);
        return user.getId();
    }

    @Override
    public User getByLogin(String login) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE login = :login", User.class)
                .setParameter("login", login)
                .uniqueResult();
    }

    @Override
    public User get(Long userID) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE id = :id", User.class)
                .setParameter("id", userID)
                .uniqueResult();
    }

    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public boolean delete(Long userID) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("DELETE User WHERE id = :id")
                .setParameter("id", userID);
        return query.executeUpdate() > 0;
    }

    @Override
    public List<User> getAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM User", User.class).list();
    }

    @Override
    public List<User> getAllLimit(Integer offset, Integer noOfRows) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("FROM User", User.class)
                .setFirstResult(offset)
                .setMaxResults(noOfRows);
        this.numberOfRecords = Integer.valueOf(sessionFactory.getCurrentSession()
                .createQuery("SELECT count(*) FROM User").uniqueResult().toString());
        return query.list();
    }

    @Override
    public List<User> getUsersSorted(Integer offset, Integer noOfRows, String orderBy, Boolean isDesc) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("FROM User ORDER BY " + orderBy + (isDesc ? " DESC" : " ASC"))
                .setFirstResult(offset)
                .setMaxResults(noOfRows);
        this.numberOfRecords = Integer.valueOf(sessionFactory.getCurrentSession()
                .createQuery("SELECT count(*) FROM User").uniqueResult().toString());
        return query.list();
    }

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

    @Override
    public Integer getNumberOfRecords() {
        return this.numberOfRecords;
    }

}