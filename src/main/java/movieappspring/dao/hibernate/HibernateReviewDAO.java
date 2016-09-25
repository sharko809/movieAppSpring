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

    /**
     * Creates a record for new review in database
     *
     * @param review review to be added to database
     * @return ID of (specified by mapping in <code>Review</code> class) created review. If review to some reasons
     * hasn't been created - returns null.
     */
    @Override
    public Long create(Review review) {
        sessionFactory.getCurrentSession().save(review);
        return review.getId();
    }

    /**
     * Searches for review with specified ID in database
     *
     * @param reviewID ID of review to be found
     * @return <code>Review</code> object if review with given ID exists.
     */
    @Override
    public Review get(Long reviewID) {
        return (Review) sessionFactory.getCurrentSession().createQuery("FROM Review WHERE id = :id")
                .setParameter("id", reviewID)
                .uniqueResult();
    }

    /**
     * Updates review data in database
     *
     * @param review review entity to update
     */
    @Override
    public void update(Review review) {
        sessionFactory.getCurrentSession().update(review);
    }

    /**
     * Searches for reviews for movie with provided id.
     *
     * @param movieID ID of movie which this review refers to
     * @return List of <code>Review</code> objects if found any. Otherwise returns empty List.
     */
    @Override
    public List<Review> getReviewsByMovieId(Long movieID) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT r FROM Review r WHERE r.movieId = :id", Review.class)
                .setParameter("id", movieID)
                .list();
    }

    /**
     * Deletes review record from database
     *
     * @param review review to be removed from database
     */
    @Override
    public void delete(Review review) {
        sessionFactory.getCurrentSession().delete(review);
    }

}