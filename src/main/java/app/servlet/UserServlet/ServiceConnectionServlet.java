package app.servlet.UserServlet;

import app.entity.Account;
import app.entity.Service;
import app.model.AccountDataManager;
import app.model.ServiceTable;
import app.model.ServiceTariffDataManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/cabinet/user/user_cabinet/select_tariff")
public class ServiceConnectionServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ServiceConnectionServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //services and tariffs that account has
        try {
            LOG.debug("Load Service Table...");
            int serviceId = Integer.parseInt(req.getParameter("serviceId"));
            List<Service> serviceList = Collections.singletonList(ServiceTariffDataManager.getServiceById(serviceId));
            ServiceTable.loadServiceTable(req, resp, serviceList);
        } catch (Exception e) {
            LOG.error("Error value of >serviceId<!");
            e.printStackTrace();
        }

        String action = req.getParameter("action");
        if (action == null) {
            LOG.info("Action is Null!");
            return;
        }
        if (action.equals("activate")) {
            toActivate(req, resp);
        }
        if (action.equals("edit")) {
            toEdit(req);
        }
        req.getRequestDispatcher(req.getRequestURI() + ".jsp").forward(req, resp);
    }

    private void toEdit(HttpServletRequest req) {
        LOG.debug("Edit Service. ID: " + req.getParameter("serviceId") + "Tariff. ID: " + req.getParameter("tariffId"));

    }

    private void toActivate(HttpServletRequest req, HttpServletResponse resp) {
        LOG.debug("Activate Service. ID: " + req.getParameter("serviceId"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Tariff selected: " + req.getParameter("selectedTariff"));
        HttpSession session = req.getSession();
        try {
            long id = (long) session.getAttribute("id");
            long serviceId = Integer.parseInt(req.getParameter("serviceId"));
            long tariffId = Integer.parseInt(req.getParameter("selectedTariff"));
            AccountDataManager.applyServiceToAccount(id, serviceId, tariffId);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("=Wrong Disable Action!=");
        }
        LOG.debug("redirecting to cabinet");
        resp.sendRedirect( "/cabinet/user/user_cabinet");
    }
}
