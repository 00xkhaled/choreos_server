package hsos.cho.srv.scenes.boundary;

import hsos.cho.srv.login.entity.LoginValidater;
import hsos.cho.srv.scenes.adapter.ControlHtmlAdapter;
import hsos.cho.srv.scenes.control.SceneManager;
import hsos.cho.srv.settings.entity.Properties;
import org.jboss.logging.Logger;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

@WebServlet("/control")
public class ControllerServlet extends HttpServlet {

    @Inject
    SceneManager sceneManager;

    @Inject
    ControlHtmlAdapter adapter;

    @Inject
    Properties properties;

    private static final Logger log = Logger.getLogger(ControllerServlet.class.getSimpleName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        //Get act Session
        HttpSession session = req.getSession();

        if(!proveValidation(session)){
            //true: Acces denied, going to LoginServlet
            res.sendRedirect(properties.loginservlet);
            log.info("ACCESS DENIED");
            return;
        }

        res.getWriter().write(adapter.generateControlHTML());
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        //Get act Session
        HttpSession session = req.getSession();

        if(!proveValidation(session)){
            //true: Acces denied, going to LoginServlet
            res.sendRedirect(properties.loginservlet);
            log.info("ACCESS DENIED");
        }

        if(req.getParameter(Properties.scene) != null) {
            String scene = req.getParameter(Properties.scene);

            if(scene.contentEquals("stop")){
                log.info("SCENE CHANGED - STOP");
                sceneManager.stopAllScenes();
            }
            else {
                try {
                    int sceneId = Integer.parseInt(scene);
                    log.info("SCENE CHANGED - SCENE" + sceneId);
                    sceneManager.changeState(sceneId);

                } catch (NumberFormatException e) {
                    log.error("WRONG NUMBER FORMAT");
                }
            }
        }
    }

    public boolean proveValidation(HttpSession session){

        LoginValidater validater = (LoginValidater)session.getAttribute(Properties.validater);

        if(validater != null) {
            if (validater.isValidated()) return true;
        }

        return false;
    }
}
