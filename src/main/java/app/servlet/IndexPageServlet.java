package app.servlet;

import app.constants.PageEnum;
import app.entity.Service;
import app.model.DowsnloadFile;
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

@WebServlet(urlPatterns = {""})
public class IndexPageServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ServiceTable.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Main page");
        List<Service> serviceList = ServiceTariffDataManager.getAllServicesWithoutTariffs();
        ServiceTable.loadServiceTable(req, resp, serviceList);
        LOG.info("Service list has been load.");

        if (req.getParameter("download") != null) {
            DowsnloadFile.downloadServices(resp, getServletContext().getRealPath(""));
        }

        req.getRequestDispatcher(PageEnum.INDEX_JSP.getValue()).forward(req, resp);
    }

}