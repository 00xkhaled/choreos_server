package hsos.cho.srv.feedback.control;

import hsos.cho.srv.feedback.entity.Feedback;
import java.util.List;

/**
 * @author Lukas Grewe
 * Represents an Interface to Acces the FeedbackRepository from boundary
 * Its Injected in FeedbackBoundary
 */

public interface FeedbackManager {
    List<Feedback> getFeedbacksAsList();
    void addFeedback(String text);
    void deleteFeedback(long id);
}
