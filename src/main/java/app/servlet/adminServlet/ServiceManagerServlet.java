package app.servlet.adminServlet;

import app.entity.Service;
import app.factory.impl.DMFactoryImpl;
import app.service.ServiceTable;
import app.service.language;
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
        language.checkLanguage(req, resp);
        List<Service> serviceList = DMFactoryImpl.getInstance().getServiceTariffDM().getAllServicesWithoutTariffs();
        ServiceTable.loadServiceTable(req, serviceList);
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

        DMFactoryImpl.getInstance().getServiceTariffDM().applyTariff(sId, tariffName, tariffDescription, tariffPrice);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LOG.info("Removing tariff");
        String tariffName = req.getParameter("tariffName");
        DMFactoryImpl.getInstance().getServiceTariffDM().removeTariff(tariffName);
    }
}
