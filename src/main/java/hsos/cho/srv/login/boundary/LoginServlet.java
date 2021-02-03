package hsos.cho.srv.login.boundary;

import hsos.cho.srv.login.adapter.LoginHtmlAdapter;
import hsos.cho.srv.login.entity.LoginValidater;
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

/**
 * @author Lukas Grewe
 * LoginServlet is used to validate/authenticate the session
 * */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    //is used to generate template/login.html
    @Inject private LoginHtmlAdapter adapter;
    //used to get some values from properties
    @Inject private Properties properties;
    //Logger for this class
    private static final Logger log = Logger.getLogger(LoginServlet.class.getSimpleName());

    /**
     * @author Lukas Grewe
     * get the Login Page or if validation = true -> redirect to controllerServlet
     * */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //Get actual HttpSession
        HttpSession session = req.getSession();
        if (session.getAttribute(properties.validater) == null) {
            //initialize LoginValidater for new HttpSession
            session.setAttribute(properties.validater, new LoginValidater());
        }
        //Get LoginValidater for actual Session
        LoginValidater val = (LoginValidater) session.getAttribute(properties.validater);
        if (val.isValidated()){
            //if Session is already validated -> redirect to controllerServlet
            res.sendRedirect(properties.controllerservlet);
            return;
        }
        //else
        //set retry = true;
        boolean retry = false;
        if(val.getLoginTry() > 0) retry = true;
        //generate response
        res.setStatus(200);
        res.getWriter().write(adapter.generateLoginHTML(retry));
    }

    /**
     * @author Lukas Grewe
     * Method gets request Parameter username/password and hand over to the LoginValidater,
     * that checks the parameter with the credentials in properties
     * */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //Get actual HttpSession
        HttpSession session = req.getSession();
        if (session.getAttribute(properties.validater) == null) {
            //initialize LoginValidater for HttpSession if validater not exist
            session.setAttribute(properties.validater, new LoginValidater());
        }
        //Get LoginServlet Data from Request
        LoginValidater validater = (LoginValidater)session.getAttribute(properties.validater);
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //Check for NO LoginData (username/password) in Request
        if (username == null || password == null) {
            //true: send LoginServlet HTML Page
            res.getWriter().write(adapter.generateLoginHTML(true));
            return;
        }
        //Check username and password
        validater.validate(username, password);
        if (validater.isValidated()) {
            //Username/Password correct ... Session is validated
            log.info("ACCESS ALLOWED");
        } else {
            //Username/Password incorrect ... Session not validated
            log.info("ACCESS DENIED - Wrong username/password");
        }
    }
}
