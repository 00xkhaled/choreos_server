package hsos.cho.srv.settings.adapter;

import hsos.cho.srv.settings.entity.Properties;
import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SettingsHtmlAdapter {
    @Inject
    Properties properties;

    @ResourcePath("settings.html")
    Template settingsPage;

    public String generateSettingsHtml(boolean changedSettings){
        return settingsPage.data("serverurl", properties.serverurl)
                .data("ticketurl", properties.ticketTicketUrl)
                .data("date", properties.ticketTicketDate)
                .data("changedSettings", changedSettings)
                .render();
    }
}
