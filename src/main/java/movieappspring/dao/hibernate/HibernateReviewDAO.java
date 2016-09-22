package movieappspring.dao.hibernate;

import movieappspring.dao.ReviewDAO;
import movieappspring.entities.Review;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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

    public Long create(Long userID, Long movieID, Date postDate, String reviewTitle, Integer rating, String reviewText) {
        Review review = new Review();
        review.setUserId(userID);
        review.setMovieId(movieID);
        review.setPostDate(postDate);
        review.setTitle(reviewTitle);
        review.setRating(rating);
        review.setReviewText(reviewText);
        sessionFactory.getCurrentSession().save(review);
        return review.getId();
    }

    public Review get(Long reviewID) {
        return (Review) sessionFactory.getCurrentSession().createQuery("FROM Review WHERE id = :id")
                .setParameter("id", reviewID)
                .uniqueResult();
    }

    public void update(Review review) {
        sessionFactory.getCurrentSession().update(review);
    }

    public List<Review> getReviewsByMovieId(Long movieID) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT r FROM Review r WHERE r.movieId = :id", Review.class)
                .setParameter("id", movieID)
                .list();
    }

    public boolean delete(Long reviewID) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("DELETE Review WHERE id = :id")
                .setParameter("id", reviewID);
        return query.executeUpdate() > 0;
    }

}