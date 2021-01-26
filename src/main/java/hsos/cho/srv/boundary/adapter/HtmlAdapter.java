package hsos.cho.srv.boundary.adapter;

import hsos.cho.srv.control.SceneManager;
import hsos.cho.srv.properties.Settings;
import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@ApplicationScoped
public class HtmlAdapter {

    @Inject
    SceneManager sceneManager;

    @ResourcePath("controller.html")
    Template controllerPage;

    @ResourcePath("login.html")
    Template loginPage;

    @ResourcePath("settings.html")
    Template settingsPage;

    public String generateControlHTML() {
        return controllerPage.data("serverurl", Settings.controllerUrl)
                                .data("scenes", sceneManager.getScenesAsList())
                                .data("stop", sceneManager.getStop())
                                .data("controllerurl", Settings.controllerUrl)
                                .data("ticketsettingurl", Settings.ticketSettingsUrl)
                                .render();
    }

    public String generateLoginHTML(){
        return loginPage.data("serverurl", Settings.loginUrl)
                            .render();
    }

    public String generateSettingsHtml(boolean changedSettings){

        return settingsPage.data("controllerurl", Settings.controllerUrl)
                            .data("ticketurl", Settings.ticketTicketUrl)
                            .data("ticketsettingsurl", Settings.ticketSettingsUrl)
                            .data("date", Settings.ticketTicketDate)
                            .data("changedSettings", changedSettings)
                            .render();
    }

}
