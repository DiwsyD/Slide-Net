package app.servlet;

import app.entity.Service;
import app.model.ServiceTable;
import app.model.ServiceTariffDataManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class IndexPageServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ServiceTable.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Service> serviceList = ServiceTariffDataManager.getAllServicesWithoutTariffs();
        ServiceTable.loadServiceTable(req, resp, serviceList);
        LOG.info("Service list has been load.");
        req.getRequestDispatcher( req.getRequestURI() + ".jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //
    }
}
