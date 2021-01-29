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

@WebServlet("/settings")
public class Settings extends HttpServlet {

    @Inject
    SettingsHtmlAdapter adapter;

    @Inject
    Properties properties;

    private static final Logger log = Logger.getLogger(Settings.class.getSimpleName());

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

        res.getWriter().write(adapter.generateSettingsHtml(false));
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

        String ticketurl = req.getParameter("ticketurl");
        String date = req.getParameter("ticketdate");

        if(ticketurl != null || date != null) {
            log.info("TICKETURL/TICKETDATE CHANGED");

            Properties.ticketTicketUrl = ticketurl;

            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate ld = LocalDate.parse(date, dtf);
                Properties.ticketTicketDate = ld;
            } catch (Exception e){
                log.error("Falsches Datum Format");
                res.getWriter().write(adapter.generateSettingsHtml(false));
                return;
            }

            Properties.ticketTicketUrl = ticketurl;

            res.getWriter().write(adapter.generateSettingsHtml(true));
            return;
        }

        res.getWriter().write(adapter.generateSettingsHtml(false));
    }

    public boolean proveValidation(HttpSession session) {

        LoginValidater validater = (LoginValidater) session.getAttribute(Properties.validater);

        if (validater != null) {
            if (validater.isValidated()) return true;
        }

        return false;
    }

}
