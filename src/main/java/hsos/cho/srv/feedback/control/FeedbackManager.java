package hsos.cho.srv.feedback.control;

import hsos.cho.srv.feedback.entity.Feedback;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class FeedbackManager {
    private Map<String, Feedback> feedbacks = new ConcurrentHashMap<>();
}
