package hsos.cho.srv.settings.adapter;

import hsos.cho.srv.settings.entity.Properties;
import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author Lukas Grewe
 * Adapter is used to render the templates/settings.html page
 */

@ApplicationScoped
public class SettingsHtmlAdapter {
    //using some properties from this class
    @Inject private Properties properties;
    //inject settings.html template
    @ResourcePath("settings.html") private Template settingsPage;

    /**
     * @author Lukas Grewe
     * @param ticketDataHasChanged used to set a banner, if something has changed in ticketdata
     * @param loginDataHasChanged used to set a banner, if something has changed in logindata
     * @return String of render settings.html page
     */
    public String generateSettingsHtml(boolean ticketDataHasChanged, boolean loginDataHasChanged){
        return settingsPage.data("serverurl", properties.serverurl)
                            .data("ticketurl", properties.ticketUrl)
                            .data("date", properties.ticketDate)
                            .data("changedTickets", ticketDataHasChanged)
                            .data("changedLogin", loginDataHasChanged)
                            .data("username", properties.username)
                            .render();
    }
}
