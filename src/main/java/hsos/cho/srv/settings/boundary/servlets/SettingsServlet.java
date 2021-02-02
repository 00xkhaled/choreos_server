package hsos.cho.srv.settings.boundary.servlets;

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
import java.util.concurrent.TimeUnit;

@WebServlet("/settings")
public class SettingsServlet extends HttpServlet {

    @Inject
    SettingsHtmlAdapter adapter;
    @Inject
    Properties properties;
    private static final Logger log = Logger.getLogger(SettingsServlet.class.getSimpleName());
    private boolean ticketDataHasChanged = false;
    private boolean loginDataHasChanged = false;

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
        res.setBufferSize(8 * 1024);
        res.getWriter().write(adapter.generateSettingsHtml(this.ticketDataHasChanged, this.loginDataHasChanged));
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //Get act Session
        HttpSession session = req.getSession();
        if (!proveValidation(session)) {
            //true: Acces denied, going to LoginServlet
            res.sendRedirect(properties.loginservlet);
        }
        String action = req.getParameter("action");
        if(action != null) {
            switch(req.getParameter("action")){
                case "changelogindata":
                    String equals = req.getParameter("equals");
                    String oldpassword = req.getParameter("oldpassword");
                    String newpassword = req.getParameter("newpassword");
                    if(oldpassword != null && newpassword != null){
                        changeLoginData(oldpassword, newpassword, equals);
                    }
                    break;
                case "changeticketdata":
                    String ticketurl = req.getParameter("ticketurl");
                    String date = req.getParameter("ticketdate");
                    changeTicketData(ticketurl, date);
                    log.info("TICKETURL/TICKETDATE CHANGED");
                    break;
            }
        }
        try { TimeUnit.MILLISECONDS.sleep(200);}
        catch(InterruptedException e) {System.out.println("Interrupt Exception!");};

        res.setStatus(200);
        res.getWriter().write(adapter.generateSettingsHtml(ticketDataHasChanged, loginDataHasChanged));

        try { TimeUnit.MILLISECONDS.sleep(200);}
        catch(InterruptedException e) {System.out.println("Interrupt Exception!");}

        this.ticketDataHasChanged = false;
        this.loginDataHasChanged = false;
    }

    private void changeLoginData(String oldpassword, String newpassword, String equals) {
        if(equals.contentEquals("true")){
            if(oldpassword.contentEquals(properties.password)){
                properties.password = newpassword;
                loginDataHasChanged = true;
                log.info("LOGINDATA CHANGED");
                return;
            }
        }
        loginDataHasChanged = false;
    }

    private void changeTicketData(String ticketurl, String date) {

        if(ticketurl != null && date != null) {
            Properties.ticketUrl = ticketurl;
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate ld = LocalDate.parse(date, dtf);
                Properties.ticketDate = ld;
            } catch (Exception e){
                log.error("Falsches Datum Format");
                this.ticketDataHasChanged = false;
            }
            Properties.ticketUrl = ticketurl;
            this.ticketDataHasChanged = true;
        }
    }

    public boolean proveValidation(HttpSession session) {
        LoginValidater validater = (LoginValidater) session.getAttribute(Properties.validater);
        if (validater != null) {
            if (validater.isValidated()) return true;
        }
        return false;
    }

}
