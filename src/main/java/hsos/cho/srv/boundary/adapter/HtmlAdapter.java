package hsos.cho.srv.boundary.adapter;

import hsos.cho.srv.control.SceneManager;
import hsos.cho.srv.properties.Properties;
import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.jboss.logging.Logger;


@ApplicationScoped
public class HtmlAdapter {

    @Inject
    SceneManager sceneManager;

    @Inject
    Properties properties;

    @ResourcePath("controller.html")
    Template controllerPage;

    @ResourcePath("login.html")
    Template loginPage;

    @ResourcePath("settings.html")
    Template settingsPage;

    public String generateControlHTML() {
        //log.trace("HtmlAdapter - generateControlHTML");

        return controllerPage.data("serverurl", properties.serverurl)
                                .data("scenes", sceneManager.getScenesAsList())
                                .data("stop", sceneManager.getStop())
                                .render();
    }

    public String generateLoginHTML(boolean reTry){
        //log.trace("HtmlAdapter - generateLoginHTML");

        return loginPage.data("serverurl", properties.serverurl)
                            .data("retry", reTry)
                            .render();
    }

    public String generateSettingsHtml(boolean changedSettings){
        //log.trace("HtmlAdapter - generateSettingsHTML");

        return settingsPage.data("serverurl", properties.serverurl)
                            .data("ticketurl", properties.ticketTicketUrl)
                            .data("date", properties.ticketTicketDate)
                            .data("changedSettings", changedSettings)
                            .render();
    }

}
