package hsos.cho.srv.settings.boundary;

import hsos.cho.srv.login.entity.LoginValidater;
import hsos.cho.srv.settings.adapter.SettingsHtmlAdapter;
import hsos.cho.srv.settings.entity.Properties;
import org.jboss.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.TimeUnit;

/**
 * @author Lukas Grewe
 * WebServlet is used to control the Ticketdata and Logindata
 */

@WebServlet("/settings")
public class SettingsServlet extends HttpServlet {

    //Used to generate the HtmlPage for SettingServlet
    @Inject SettingsHtmlAdapter adapter;
    //Using some variables from Properties
    @Inject Properties properties;
    //Logger for this class
    private static final Logger log = Logger.getLogger(SettingsServlet.class.getSimpleName());
    //to generate a "Changes Accepted" banner in Html site
    private boolean ticketDataHasChanged = false;
    private boolean loginDataHasChanged = false;

    /**
     * @author Lukas Grewe
     * GET-Request Method
     *
     * send settings.html from resources/templates if session is validated,
     * else redirect to to loginServlet
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //Get act Session
        HttpSession session = req.getSession();
        if (!proveValidation(session)) {
            //true: Acces denied, going to LoginServlet
            res.sendRedirect(properties.loginservlet);
            return;
        }
        this.ticketDataHasChanged = false;
        this.loginDataHasChanged = false;
        res.setStatus(200);
        res.getWriter().write(adapter.generateSettingsHtml(this.ticketDataHasChanged, this.loginDataHasChanged));
    }

    /**
     * @author Lukas Grewe
     * POST-Request Method
     * Changing Loginpassword or Ticketinformation on requestParameter "action"
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //Get act Session
        HttpSession session = req.getSession();
        if (!proveValidation(session)) {
            //true: Acces denied, going to LoginServlet
            res.sendRedirect(properties.loginservlet);
        }
        //Get Session parameter "action" for switching the login or ticketdata procedure
        String action = req.getParameter("action");
        if(action != null) {
            //param action is set, switch to case
            switch(req.getParameter("action")){
                case "changelogindata":
                    //variable that is set on Client site, means newpassword and newpasswordrepeat is equal
                    String equals = req.getParameter("equals");
                    String oldpassword = req.getParameter("oldpassword");
                    String newpassword = req.getParameter("newpassword");
                    if(oldpassword != null && newpassword != null){
                        //oldpassword and newpassword is set -> changing Logindata
                        changeLoginData(oldpassword, newpassword, equals);
                    }
                    break;
                case "changeticketdata":
                    String ticketurl = req.getParameter("ticketurl");
                    String date = req.getParameter("ticketdate");
                    if(ticketurl != null && date != null) {
                        changeTicketData(ticketurl, date);
                    }
                    log.info("TICKETURL/TICKETDATE CHANGED");
                    break;
            }
        }
        //Delay for saving new data in properties class
        try { TimeUnit.MILLISECONDS.sleep(200);}
        catch(InterruptedException e) {log.error("Interrupt Exception!");}

        //generate response
        res.setStatus(200);
        res.getWriter().write(adapter.generateSettingsHtml(ticketDataHasChanged, loginDataHasChanged));

        this.ticketDataHasChanged = false;
        this.loginDataHasChanged = false;
    }

    /**
     * @author Lukas Grewe
     * @param oldpassword is the password, that was set in PropertiesClass
     * @param newpassword is the password, that will be set in PropertisClass
     * @param equals is an variable, that is set on clientsite, means newpassword and newpasswordrepeat is equal
     */
    private void changeLoginData(String oldpassword, String newpassword, String equals) {
        if(equals.contentEquals("true")){
            //newpassword and newpasswordrepeat is equal
            if(oldpassword.contentEquals(properties.password)){
                //oldpassword equal password in PropertiesClass -> switching passwords
                properties.password = newpassword;
                loginDataHasChanged = true;
                log.info("LOGINDATA CHANGED");
                return;
            }
        }
        loginDataHasChanged = false;
    }

    /**
     * @author Lukas Grewe
     * @param ticketurl is the new ticketurl that will be set in Properties Class
     * @param date is the new available ticketdate that will be set in Properties Class
     */
    private void changeTicketData(String ticketurl, String date) {
        //set new ticketurl in Properties class
        Properties.ticketUrl = ticketurl;
        try {
            //try to parse the date into LocalDate class
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ld = LocalDate.parse(date, dtf);
            Properties.ticketDate = ld;
        } catch (DateTimeParseException e) {
            //catch Exception, if the date format is wrong
            log.error("Falsches Datum Format");
            this.ticketDataHasChanged = false;
        }
        this.ticketDataHasChanged = true;
    }

    /**
     * @author Lukas Grewe
     * @param session that will be checked for a valid session
     * @return true if session is validated
     * @return false if session is not validated
     */
    public boolean proveValidation(HttpSession session) {
        //Getting Validator informations from Session
        LoginValidater validater = (LoginValidater) session.getAttribute(Properties.validater);
        if (validater != null) {
            //if validater existe -> check is validated
            if (validater.isValidated()) return true;
        }
        return false;
    }

}
