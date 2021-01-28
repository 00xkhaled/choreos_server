package hsos.cho.srv.boundary.servlets;

import hsos.cho.srv.boundary.adapter.HtmlAdapter;
import hsos.cho.srv.properties.Properties;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("")
public class DefaultDispatch extends HttpServlet {

    @Inject
    Properties properties;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.sendRedirect(properties.controllerservlet);
    }


}
