package app.servlet.AdminServlet;

import app.service.AccountDataManager;
import app.service.GetPayment;
import app.service.ServiceTariffDataManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns =
        {"/cabinet/admin/admin_cabinet",
         "/cabinet/admin/settings"})
public class AdminAccountServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(AdminAccountServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("doGet: " + req.getRequestURI());
        int serviceCount = ServiceTariffDataManager.getServiceCount();
        int tariffCount = ServiceTariffDataManager.getTariffCount();
        int accountCount = AccountDataManager.getAccountCount();

        req.setAttribute("serviceCount", serviceCount);
        req.setAttribute("tariffCount", tariffCount);
        req.setAttribute("accountCount", accountCount);
        req.getRequestDispatcher(req.getRequestURI() + ".jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LOG.debug("Hello wassup?");
            int monthAccelerate = Integer.parseInt(req.getParameter("month"));
            monthAccelerate = Math.max(monthAccelerate, 1);
            LOG.debug("monthAccelerate = " + monthAccelerate);
            GetPayment.getMonthPayment(monthAccelerate);
        } catch (NumberFormatException e) {
            LOG.warn("=Invalid month accelerate number!=");
            e.printStackTrace();
        }
        //req.getRequestDispatcher(req.getRequestURI() + ".jsp").forward(req, resp);
        resp.sendRedirect( "/cabinet/admin/admin_cabinet");
    }


}