package hsos.cho.srv.properties;

import java.time.LocalDate;

public class Settings {
    //SERVER URL
    //public static final String serverUrl = "http://localhost:8080";
    public static String serverUrl = "http://167.99.254.202:8080";

    //SERVLETS
    public static final String loginServlet = "/login";
    public static final String controllerServlet = "/control";
    public static final String settingServlet = "/settings";
    public static final String defaultDispatchServlet= "";

    //SESSION PARAMETER
    public static final String validater = "validater";
    public static final String scene = "scene";

    //SERVLET URLs
    public static final String controllerUrl = serverUrl + "/control";
    public static final String loginUrl = serverUrl + "/login";
    public static final String ticketSettingsUrl = serverUrl + "/settings";

    //REST URLs
    public static final String ticketInformationUrl = serverUrl + "/tickets";

    //TICKET INFORMATION
    public static String ticketTicketUrl = "www.defaultticketurl.de";
    public static LocalDate ticketTicketDate = LocalDate.of(2021,1,1);
}
