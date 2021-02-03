package hsos.cho.srv.feedback.adapter;

import hsos.cho.srv.feedback.control.FeedbackManager;
import hsos.cho.srv.settings.entity.Properties;
import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author Lukas Grewe
 * Adapter is used to render the templates/feedback.html page
 */

@ApplicationScoped
public class FeedbackHtmlAdapter {
    //template for the feedbackPage
    @ResourcePath("feedback.html") Template feedbackPage;
    //Get the List of Feedbacks from FeedbackManager
    @Inject FeedbackManager feedbackManager;
    //to use some Properties
    @Inject Properties properties;

    public String generateFeedbackHtml(){
        return feedbackPage.data("serverurl", properties.serverurl)
                .data("feedbacks", feedbackManager.getFeedbacksAsList())
                .render();
    }
}