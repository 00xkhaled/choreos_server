package hsos.cho.srv.boundary.servlets;

import hsos.cho.srv.boundary.adapter.HtmlAdapter;
import hsos.cho.srv.control.LoginValidater;
import hsos.cho.srv.control.SceneManager;
import hsos.cho.srv.properties.Settings;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

@WebServlet("/control")
public class Controller extends HttpServlet {

    @Inject
    SceneManager sceneManager;

    @Inject
    HtmlAdapter adapter;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        System.out.println("ControllerServlet - doGet");

        //Get act Session
        HttpSession session = req.getSession();

        if(!proveValidation(session)){
            //true: Acces denied, going to Login
            res.sendRedirect(Settings.loginServlet);
            return;
        }

        res.getWriter().write(adapter.generateControlHTML());
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        System.out.println("ControllerServlet - doPost");

        //Get act Session
        HttpSession session = req.getSession();

        if(!proveValidation(session)){
            //true: Acces denied, going to Login
            res.sendRedirect(Settings.loginServlet);
        }

        if(req.getParameter(Settings.scene) != null) {
            String scene = req.getParameter(Settings.scene);
            int sceneId = Integer.parseInt(scene);
            sceneManager.changeState(sceneId);
        }

        res.getWriter().write(adapter.generateControlHTML());
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        System.out.println("ControlServlet - doPut");

        HttpSession session = req.getSession();

        if(!proveValidation(session)){
            //true: Acces denied, going to Login
            res.getWriter().write(adapter.generateLoginHTML());
            return;
        }
    }

    public boolean proveValidation(HttpSession session){

        LoginValidater validater = (LoginValidater)session.getAttribute(Settings.validater);

        if(validater != null) {
            if (validater.isValidated()) return true;
        }

        return false;
    }
}
