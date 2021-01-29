package hsos.cho.srv.feedback.adapter;

import hsos.cho.srv.settings.entity.Properties;
import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FeedbackHtmlAdapter {

    @ResourcePath("feedback.html")
    Template feedbackPage;

    @Inject
    Properties properties;

    public String generateFeedbackHtml(){
        return feedbackPage.data("serverurl", properties.serverurl)
                .render();
    }
}
