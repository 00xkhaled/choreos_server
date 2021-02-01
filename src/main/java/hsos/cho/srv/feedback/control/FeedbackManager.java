package hsos.cho.srv.feedback.control;

import hsos.cho.srv.feedback.entity.Feedback;

import java.util.List;

public interface FeedbackManager {
    public List<Feedback> getFeedbacksAsList();
    public void addFeedback(String text);
    public void deleteFeedback(long id);
}
