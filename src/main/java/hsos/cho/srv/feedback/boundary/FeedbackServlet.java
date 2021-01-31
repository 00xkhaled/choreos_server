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

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {

    @Inject
    private Properties properties;

    @Inject
    private FeedbackHtmlAdapter adapter;

    @Inject
    private FeedbackManager feedbackManager;

    private static final Logger log = Logger.getLogger(FeedbackServlet.class.getSimpleName());

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        if(!proveValidation(session)){
            //true: Acces denied, going to LoginServlet
            res.sendRedirect(properties.loginservlet);
            log.info("ACCESS DENIED");
        }

        res.getWriter().write(adapter.generateFeedbackHtml());
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        String feedbacktext = req.getParameter("feedbacktext");

        if(feedbacktext != null){
            feedbackManager.addFeedback(feedbacktext);
        }
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        if(!proveValidation(session)){
            //true: Acces denied, going to LoginServlet
            res.sendRedirect(properties.loginservlet);
            log.info("ACCESS DENIED");
        }

        if(req.getParameter("id") != null){
            long id = Long.parseLong(req.getParameter("id"));
            feedbackManager.deleteFeedback(id);
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
