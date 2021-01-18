package hsos.cho.srv.boundary.servlets;

import hsos.cho.srv.boundary.adapter.HtmlAdapter;
import hsos.cho.srv.control.LoginValidater;
import hsos.cho.srv.properties.Settings;
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
public class Setting extends HttpServlet {

    @Inject
    HtmlAdapter adapter;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        System.out.println("ControllerServlet - doGet");

        //Get act Session
        HttpSession session = req.getSession();

        if (!proveValidation(session)) {
            //true: Acces denied, going to Login
            res.sendRedirect(hsos.cho.srv.properties.Settings.loginServlet);
            return;
        }

        res.getWriter().write(adapter.generateSettingsHtml(false));
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        System.out.println("ControllerServlet - doPost");

        //Get act Session
        HttpSession session = req.getSession();

        if (!proveValidation(session)) {
            //true: Acces denied, going to Login
            res.sendRedirect(hsos.cho.srv.properties.Settings.loginServlet);
        }

        String ticketurl = req.getParameter("ticketurl");
        String date = req.getParameter("ticketdate");

        if(ticketurl != null || date != null) {

            Settings.ticketTicketUrl = ticketurl;

            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate ld = LocalDate.parse(date, dtf);
                Settings.ticketTicketDate = ld;
            } catch (Exception e){
                System.out.println("ERROR: Falsches Datum Format");
                res.getWriter().write(adapter.generateSettingsHtml(false));
                return;
            }

            Settings.ticketTicketUrl = ticketurl;

            res.getWriter().write(adapter.generateSettingsHtml(true));
            return;
        }

        res.getWriter().write(adapter.generateSettingsHtml(false));
    }

    public boolean proveValidation(HttpSession session) {

        LoginValidater validater = (LoginValidater) session.getAttribute(hsos.cho.srv.properties.Settings.validater);

        if (validater != null) {
            if (validater.isValidated()) return true;
        }

        return false;
    }

}
