package hsos.cho.srv.boundary.servlets;

import hsos.cho.srv.boundary.adapter.HtmlAdapter;
import hsos.cho.srv.control.LoginValidater;
import hsos.cho.srv.properties.Properties;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
 * Author: Lukas Grewe
 * created on 20.11.2020
 * Function:
 * */

@WebServlet("/login")
public class Login extends HttpServlet {

    @Inject
    HtmlAdapter adapter;

    @Inject
    Properties properties;

    private static final Logger log = Logger.getLogger(Login.class.getName());

    /*
     * Returns Login-web-page
     * */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        //Get actual HttpSession
        HttpSession session = req.getSession();

        //initialize LoginValidater for new HttpSession
        if (session.getAttribute(properties.validater) == null)
            session.setAttribute(properties.validater, new LoginValidater());

        //Get LoginValidater for actual Session
        LoginValidater val = (LoginValidater) session.getAttribute(properties.validater);

        //if Session is already validated
        if (val.isValidated()){
            res.sendRedirect(properties.controllerservlet);
        }

        //got To LoginServlet Post-Method
        doPost(req, res);
    }

    /*
     * proofs login data with Validater
     * if validation false:
     *   return Loginpage again
     *
     * if validation true:
     *   forward to ControlServlet
     * */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //Get actual HttpSession
        HttpSession session = req.getSession();

        //initialize LoginValidater for HttpSession
        if (session.getAttribute(properties.validater) == null)
            session.setAttribute(properties.validater, new LoginValidater());

        //Get Login Data from Request
        LoginValidater validater = (LoginValidater)session.getAttribute(properties.validater);
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //Check for NO LoginData (username/password) in Request
        if (username == null || password == null) {
            //true: send Login HTML Page
            res.getWriter().write(adapter.generateLoginHTML(false));
            return;
        }

        //Check username and password
        validater.validate(username, password);

        if (validater.isValidated()) {
            //Username/Password correct, go to Controller Servlet
            log.info("ACCESS ALLOWED");
            RequestDispatcher rd = req.getRequestDispatcher(properties.controllerservlet);
            res.sendRedirect(properties.controllerservlet);
        } else {
            //Username/Password incorrect -> try login again
            log.info("ACCESS DENIED - Wrong username/password");
            res.getWriter().write(adapter.generateLoginHTML(true));
        }
    }
}
