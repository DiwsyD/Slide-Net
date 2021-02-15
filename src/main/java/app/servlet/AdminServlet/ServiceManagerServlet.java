package app.servlet.AdminServlet;

import app.entity.Service;
import app.entity.Tariff;
import app.model.ServiceTable;
import app.model.ServiceTariffDataManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/cabinet/admin/service_mng"})
public class ServiceManagerServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ServiceManagerServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Service> serviceList = ServiceTariffDataManager.getAllServicesWithoutTariffs();
        ServiceTable.loadServiceTable(req, resp, serviceList);
        LOG.info("Service list has been load.");

        req.getRequestDispatcher( req.getRequestURI() + ".jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LOG.debug("Adding new Tariff");
        String serviceId = req.getParameter("service");
        String tariffName = req.getParameter("tariffName");
        String tariffDescription = req.getParameter("tariffDescription");
        int tariffPrice = -1;
        try {
            tariffPrice = Integer.parseInt(req.getParameter("tariffPrice"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        int sId = Integer.parseInt(serviceId);

        ServiceTariffDataManager.applyTariff(sId, tariffName, tariffDescription, tariffPrice);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LOG.info("Removing tariff");
        String tariffName = req.getParameter("tariffName");
        ServiceTariffDataManager.removeTariff(tariffName);
    }
}
