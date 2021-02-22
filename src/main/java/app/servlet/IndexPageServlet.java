package app.servlet;

import app.entity.Service;
import app.factory.impl.DMFactoryImpl;
import app.service.DownloadFile;
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

@WebServlet(urlPatterns = {""})
public class IndexPageServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ServiceTable.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Main page");
        language.checkLanguage(req, resp);
        List<Service> serviceList = DMFactoryImpl.getInstance().getServiceTariffDM().getAllServicesWithoutTariffs();
        ServiceTable.loadServiceTable(req, serviceList);
        LOG.info("Service list has been load.");

        if (req.getParameter("download") != null) {
            DownloadFile.downloadServices(resp);
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

}