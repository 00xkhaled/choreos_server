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

/**
 * @author Lukas Grewe
 * WebServlet to control the Scenes
 * it communicate to the SceneManager when a new Scene will be activated int the Post-Request
 */
@WebServlet("/control")
public class ControllerServlet extends HttpServlet {
    //to do operations on the scenes
    @Inject private SceneManager sceneManager;
    //to render the template/controller.html page
    @Inject private ControlHtmlAdapter adapter;
    //to use some properties
    @Inject private Properties properties;
    //Logger for this class
    private static final Logger log = Logger.getLogger(ControllerServlet.class.getSimpleName());

    /**
     * @author Lukas Grewe
     * if the session is validated, the method returns the template/control.html
     * else redirection to the loginServlet
     */
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
        //build response
        res.setStatus(200);
        res.getWriter().write(adapter.generateControlHTML());
    }

    /**
     * @author Lukas Grewe
     * if the session is validated, the method switching the scenes to a new scene or stopScene
     * else redirection to the loginServlet
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //Get act Session
        HttpSession session = req.getSession();
        if(!proveValidation(session)) {
            //true: Acces denied, going to LoginServlet
            res.sendRedirect(properties.loginservlet);
            log.info("ACCESS DENIED");
            return;
        }
        //get scene parameter from Session
        String scene = req.getParameter(Properties.scene);
        if(scene != null) {
            //scene is set -> switch scenes
            if(scene.contentEquals("stop")){
                //stop scene arrived
                log.info("SCENE SWITCHED - STOP");
                sceneManager.stopAllScenes();
            }
            else {
                //new sceneId arrived
                try {
                    //try to parse the new scene id
                    int sceneId = Integer.parseInt(scene);
                    log.info("SCENE SWITCHED - SCENE" + sceneId);
                    //switch scene id with scenemanager
                    sceneManager.changeState(sceneId);
                } catch (NumberFormatException e) {
                    //catch Exception when parse id failed
                    log.error("WRONG NUMBER FORMAT");
                }
            }
        }
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
