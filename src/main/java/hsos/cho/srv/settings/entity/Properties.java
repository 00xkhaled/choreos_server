package hsos.cho.srv.settings.entity;

import io.quarkus.arc.config.ConfigProperties;
import java.time.LocalDate;

/**
 * @author: Lukas Grewe
 * This class is used to insert the config-paramerts from application.properties file
 * It holds the meta-informations for the Application
 */
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

    public static String username= "choreos";
    //pw: "choreos" MD5ecndoded
    public static String password = "4d120dff5bb8db9853e69e627c554bfe";

    /*
    Non Injected Data
     */

    //SESSION PARAMETER
    public static String validater = "validater";
    public static String scene = "scene";

    //TICKET INFORMATION
    public static String ticketUrl = "www.defaultticketurl.de";
    public static LocalDate ticketDate = LocalDate.of(2020,12,12);

    public Properties(){ }
}
