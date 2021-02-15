package app.servlet.UserServlet;

import app.entity.AccountService;
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

    protected void redirectToCabinet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher( "/cabinet/user/user_cabinet").forward(req, resp);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("SELECT TARIFF");
        //services and tariffs that account has
        try {
            int serviceId = Integer.parseInt(req.getParameter("serviceId"));
            List<Service> serviceList = Collections.singletonList(ServiceTariffDataManager.getServiceById(serviceId));
            ServiceTable.loadServiceTable(req, resp, serviceList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (req.getParameter("activate") != null) {
            toActivate(req, resp);
        }
        if (req.getParameter("edit") != null) {
            toEdit(req);
        }
        req.getRequestDispatcher(req.getRequestURI() + ".jsp").forward(req, resp);
    }

    private void toEdit(HttpServletRequest req) {
        LOG.debug("Edit ServiceID: " + req.getParameter("serviceId"));
        LOG.debug("Edit TariffID: " + req.getParameter("tariffId"));

    }

    private void toActivate(HttpServletRequest req, HttpServletResponse resp) {
        LOG.debug("Edit ServiceID: " + req.getParameter("serviceId"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Select tariff: " + req.getParameter("selectedTariff"));
        HttpSession session = req.getSession();
        try {
            long id = (long) session.getAttribute("id");
            long serviceId = Integer.parseInt(req.getParameter("serviceId"));
            long tariffId = Integer.parseInt(req.getParameter("selectedTariff"));
            AccountDataManager.activateService(id, serviceId, tariffId);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("=Wrong Disable Action!=");
        }
        redirectToCabinet(req, resp);
    }
}
