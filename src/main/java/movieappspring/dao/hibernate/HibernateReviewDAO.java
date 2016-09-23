package movieappspring.dao.hibernate;

import movieappspring.dao.ReviewDAO;
import movieappspring.entities.Review;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class that accesses the database and performs any activity connected with database using hibernate.
 * <p>
 * This particular class deals with review data in database.
 */
@Repository
@Transactional
public class HibernateReviewDAO implements ReviewDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public HibernateReviewDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long create(Review review) {
        sessionFactory.getCurrentSession().save(review);
        return review.getId();
    }

    @Override
    public Review get(Long reviewID) {
        return (Review) sessionFactory.getCurrentSession().createQuery("FROM Review WHERE id = :id")
                .setParameter("id", reviewID)
                .uniqueResult();
    }

    @Override
    public void update(Review review) {
        sessionFactory.getCurrentSession().update(review);
    }

    @Override
    public List<Review> getReviewsByMovieId(Long movieID) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT r FROM Review r WHERE r.movieId = :id", Review.class)
                .setParameter("id", movieID)
                .list();
    }

    @Override
    public void delete(Review review) {
        sessionFactory.getCurrentSession().delete(review);
    }

}