package hsos.cho.srv.login.adapter;

import hsos.cho.srv.settings.entity.Properties;
import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author Lukas Grewe
 * Adapter is used to render the templates/login.html page
 */

@ApplicationScoped
public class LoginHtmlAdapter {
    //inject template
    @ResourcePath("login.html") Template loginPage;
    //to use some properties
    @Inject Properties properties;

    /**
     * @param reTry is used to show a banner if its a login retry (password false)
     */
    public String generateLoginHTML(boolean reTry){
        return loginPage.data("serverurl", properties.serverurl)
                .data("retry", reTry)
                .render();
    }
}
