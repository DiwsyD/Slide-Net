package app.servlet.AdminServlet;

import app.entity.Account;
import app.entity.Service;
import app.model.ServiceTariffDataManager;
import app.model.AccountUserDataManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns =
        {"/cabinet/admin/admin_cabinet",
         "/cabinet/admin/settings"})
public class AdminAccountServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(AdminAccountServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LOG.debug("doGet: " + req.getRequestURI());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(req.getRequestURI() + ".jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        
        this.doGet(req, resp);
    }

    private String getPage(String URI) {
        String[] pages = URI.split("/");
        return pages[pages.length-1];
    }


}