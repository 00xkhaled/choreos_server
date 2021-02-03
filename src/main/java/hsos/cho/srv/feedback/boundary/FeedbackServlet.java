package hsos.cho.srv.feedback.boundary;

import hsos.cho.srv.feedback.adapter.FeedbackHtmlAdapter;
import hsos.cho.srv.feedback.control.FeedbackManager;
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
 * WebServlet that handles Feedback Messages
 * Implements Get, Post, Delete Functions to edit the
 */
@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {
    //for using some propertie variables
    @Inject private Properties properties;
    //used to render the templates/feedback.html file
    @Inject private FeedbackHtmlAdapter adapter;
    //Interface to the FeedbackRepository (holding CRUD-Functions)
    @Inject private FeedbackManager feedbackManager;
    //Logger for this class
    private static final Logger log = Logger.getLogger(FeedbackServlet.class.getSimpleName());

    /**
     * @author Lukas Grewe
     * @return template/feedback.html site if session is validated
     * else redirect to loginServlet
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //Get Session from request
        HttpSession session = req.getSession();
        if(!proveValidation(session)){
            //true: Acces denied, going to LoginServlet
            res.sendRedirect(properties.loginservlet);
            log.info("ACCESS DENIED");
            return;
        }
        //render template/feedback.html page
        res.getWriter().write(adapter.generateFeedbackHtml());
    }

    /**
     * @author Lukas Grewe
     * Method adds a new Feedback to the database, if the text exist
     * @return
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //Get Session from request
        HttpSession session = req.getSession();
        //Get Text from request
        String feedbacktext = req.getParameter("feedbacktext");
        if(feedbacktext != null){
            //if text exist -> store Feedback in database
            feedbackManager.addFeedback(feedbacktext);
        }
    }

    /**
     * @author Lukas Grewe
     * Method deletes a Feedback from database, an id, that was
     * set in request if session is valid
     * else redirect to loginServlet
     * @return
     */
    public void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        //get Session from request
        HttpSession session = req.getSession();
        if(!proveValidation(session)){
            //true: Acces denied, going to LoginServlet
            res.sendRedirect(properties.loginservlet);
            log.info("ACCESS DENIED");
        }
        if(req.getParameter("id") != null){
            //Parameter id exist -> delete Feedback with this id
            long id = Long.parseLong(req.getParameter("id"));
            feedbackManager.deleteFeedback(id);
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
