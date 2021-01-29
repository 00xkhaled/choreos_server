package hsos.cho.srv.login.adapter;

import hsos.cho.srv.settings.entity.Properties;
import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LoginHtmlAdapter {

    @ResourcePath("login.html")
    Template loginPage;

    @Inject
    Properties properties;

    public String generateLoginHTML(boolean reTry){
        return loginPage.data("serverurl", properties.serverurl)
                .data("retry", reTry)
                .render();
    }
}
