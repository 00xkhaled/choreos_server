package hsos.cho.srv.properties;

public class Settings {
    //SERVLETS
    public static String loginServlet = "/login";
    public static String controllerServlet ="/control";
    public static String defaultDispatchServlet="";

    //HTML PAGES
    public static String loginHtml = "/html/login.html";
    public static String controllerHtml = "/html/controller.html";

    //SESSION PARAMETER
    public static String validater = "validater";
    public static String scene = "scene";

    //LOCALHOST URLs
    public static String controllerUrl = "http://localhost:8080/control";
    public static String loginUrl = "http://locahost:8080/login";

    //SERVER URLs
    //public static String controllerUrl = "http://localhost:8080/control";
    //public static String loginUrl = "http://locahost:8080/login";

}
