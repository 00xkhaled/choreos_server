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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukas Grewe
 * Managing the database acces to handle the feedback messages
 */

@RequestScoped
public class FeedbackRepository implements FeedbackManager {
    //Injected to handle the databaseaccess
    @Inject @PersistenceContext private EntityManager em;
    //Logger for this class
    private static final Logger log = Logger.getLogger(FeedbackRepository.class.getSimpleName());

    public FeedbackRepository() { }

    /**
     * @author Lukas Grewe
     * @return a List of all Feedbacks that are stored in the feedback database
     */
    @Override
    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Feedback> getFeedbacksAsList() {
        //Query to get all Feedbacks from database
        Query query = em.createQuery("SElECT e FROM Feedback e ORDER BY date DESC");
        //initialization of an other List to clone the Feedbacks from QueryResult
        List<Feedback> list = new ArrayList<>();

        //Clone all Feedbacks from querylist to list
        for(Object f : query.getResultList()){
            //cloning feedback values
            list.add(((Feedback)f).clone());
            if(((Feedback)f).getWasSeen() == false)
                //mark Feedback as seen
                setFeedbackAsSeen(((Feedback)f).getId());
        }

        return list;
    }

    /**
     * @author Lukas Grewe
     * Method adding a new Feedback to the database
     * @param text text for the Feedback
     */
    @Override
    @Transactional(value = Transactional.TxType.REQUIRED)
    public void addFeedback(String text) {
        log.info("NEW FEEDBACK");
        em.persist(new Feedback(text));
    }

    /**
     * @author Lukas Grewe
     * Method deleting an existing Feedback from the database
     * @param id is the feedback_id that will delete
     */
    @Override
    @Transactional(value = Transactional.TxType.REQUIRED)
    public void deleteFeedback(long id) {
        log.info("FEEDBACK DELETED");
        Feedback fb = em.find(Feedback.class, id);
        em.remove(fb);
    }

    /**
     * @author Lukas Grewe
     * Method deleting an existing Feedback from the database
     * @param id is the feedback_id that will merge with variable "wasSeen"=true
     */
    @Transactional(value = Transactional.TxType.REQUIRED)
    public void setFeedbackAsSeen(long id) {
        Feedback fb = em.find(Feedback.class, id);
        fb.setWasSeen(true);
        em.merge(fb);
    }
}
