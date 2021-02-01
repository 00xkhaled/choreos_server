package hsos.cho.srv.settings.entity;

import io.quarkus.arc.config.ConfigProperties;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public static String username= "choreos";
    public static String password = "4d120dff5bb8db9853e69e627c554bfe";

    /*
    Non Injected Data
     */

    //SESSION PARAMETER
    public static final String validater = "validater";
    public static final String scene = "scene";

    //TICKET INFORMATION
    public static String ticketUrl = "www.defaultticketurl.de";
    public static LocalDate ticketDate = LocalDate.of(2020,12,12);

    public Properties(){ }
}
