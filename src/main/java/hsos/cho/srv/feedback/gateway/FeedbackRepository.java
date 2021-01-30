package hsos.cho.srv.feedback.gateway;

import hsos.cho.srv.feedback.control.FeedbackManager;
import hsos.cho.srv.feedback.entity.Feedback;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;


@RequestScoped
public class FeedbackRepository implements FeedbackManager {

    @Inject
    @PersistenceContext
    private EntityManager em;

    private static final Logger log = Logger.getLogger(FeedbackRepository.class.getSimpleName());

    public FeedbackRepository() { }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Feedback> getFeedbacksAsList() {
        Query query = em.createQuery("SElECT e FROM Feedback e");
        return query.getResultList();
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED)
    public void addFeedback(String text) {
        log.info("NEW FEEDBACK");
        em.persist(new Feedback(text));
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED)
    public void deleteFeedback(long id) {
        log.info("FEEDBACK WITH " + id + " DELETED");
        Feedback fb = em.find(Feedback.class, id);
        em.remove(fb);
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED)
    public void deleteAllFeedbacks() {
        log.info("ALL FEEDBACKS DELETED");
        em.createQuery("DELETE FROM Feedback").executeUpdate();
    }
}
