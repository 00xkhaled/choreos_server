package hsos.cho.srv.boundary.servlets;

import hsos.cho.srv.boundary.adapter.HtmlAdapter;
import hsos.cho.srv.properties.Settings;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("")
public class DefaultDispatch extends HttpServlet {

    @Inject
    HtmlAdapter adapter;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        System.out.println("DefaultDispatchServlet - doGet");

        res.sendRedirect(Settings.controllerServlet);
    }


}
