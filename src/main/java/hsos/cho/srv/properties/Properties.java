package hsos.cho.srv.properties;

import io.quarkus.arc.config.ConfigProperties;
import java.time.LocalDate;

@ConfigProperties(prefix = "config")
public class Properties {

    /*
    Inject Data from application.properties with prefix config.
     */

    //SERVER URL
    public String serverurl;

    //SERVLETS
    public String loginservlet;
    public String controllerservlet;
    public String settingsservlet;
    public String defaultdispatchservlet = "";

    /*
    Non Injected Data
     */

    //SESSION PARAMETER
    public static final String validater = "validater";
    public static final String scene = "scene";

    //TICKET INFORMATION
    public static String ticketTicketUrl = "www.defaultticketurl.de";
    public static LocalDate ticketTicketDate = LocalDate.of(2021,1,1);

    public Properties(){ }

}
