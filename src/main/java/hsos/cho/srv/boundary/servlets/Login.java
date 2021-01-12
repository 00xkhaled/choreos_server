package hsos.cho.srv.boundary.servlets;

import hsos.cho.srv.control.LoginValidater;
import hsos.cho.srv.properties.Settings;

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

    /*
     * Returns Login-web-page
     * */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        System.out.println("LoginServlet: doGet()");

        //Get actual HttpSession
        HttpSession session = req.getSession();

        //initialize LoginValidater for HttpSession
        if (session.getAttribute(Settings.validater) == null)
            session.setAttribute(Settings.validater, new LoginValidater());

        //Get LoginValidater for actual Session
        LoginValidater val = (LoginValidater) session.getAttribute(Settings.validater);

        //if Session is already validated
        if (val.isValidated()) res.sendRedirect(Settings.controllerServlet);

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
        System.out.println("LoginServlet: doPost()");

        //Get actual HttpSession
        HttpSession session = req.getSession();

        //initialize LoginValidater for HttpSession
        if (session.getAttribute(Settings.validater) == null)
            session.setAttribute(Settings.validater, new LoginValidater());

        //Get Login Data from Request
        LoginValidater validater = (LoginValidater)session.getAttribute(Settings.validater);
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //Check for NO LoginData (username/password) in Request
        if (username == null || password == null) {
            //true: send Login HTML Page
            RequestDispatcher rd = req.getRequestDispatcher(Settings.loginHtml);
            rd.forward(req, res);
            return;
        }

        //Check username and password
        validater.validate(username, password);

        if (validater.isValidated()) {
            //Username/Password correct, go to Controller Servlet
            System.out.println("Login: doPost - isValidated");

            RequestDispatcher rd = req.getRequestDispatcher(Settings.controllerServlet);
            res.sendRedirect(Settings.controllerServlet);
        } else {
            //Username/Password incorrect -> try login again
            System.out.println("Login: doPost - wrong Logindata - try again");

            RequestDispatcher rd = req.getRequestDispatcher(Settings.loginHtml);
            rd.forward(req, res);
        }
    }
}
