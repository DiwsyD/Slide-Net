package app.servlet.AdminServlet;

import app.entity.Service;
import app.entity.Tariff;
import app.model.AccountUserDataManager;
import app.model.ServiceTariffDataManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/cabinet/admin/service_mng"})
public class ServiceManagerServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ServiceManagerServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Service> serviceList = ServiceTariffDataManager.getAllServicesWithoutTariffs();
        LOG.info("Service list has been load.");

        String serId = req.getParameter("service");

        String orderBy = req.getParameter("orderBy");
        String desc = "";
        long sId = serviceList.get(0).getId();
        if (serId != null) {
            sId = Integer.parseInt(serId);
        }


        if (orderBy == null || !orderBy.contains("name") && !orderBy.contains("price")) {
            orderBy = "id";
        }
        String[] sort = orderBy.split("_");
        if (sort.length > 1) {
            orderBy = sort[0];
            desc = sort[1];
        }

        //Pagination
        int page = 1;
        int pagePaginSize = 3;
        int maxPage = (int)Math.ceil((double) ServiceTariffDataManager.getServiceTariffCount(sId) / pagePaginSize);

        String pageNum = req.getParameter("page");
        if(pageNum != null) {
            try {
                page = Integer.parseInt(pageNum);
            } catch (NumberFormatException e) {
                LOG.debug("Wrong pagination Data!");
                e.printStackTrace();
            } finally {
                if (page < 1) {
                    page = 1;
                }
            }
        }

        if (page > maxPage) {
            page = maxPage;
        }

        List<Tariff> tariffList =  ServiceTariffDataManager.getCertainServiceTariffs(sId, page, pagePaginSize, orderBy, desc);

        req.setAttribute("serviceList", serviceList);
        req.setAttribute("tariffList", tariffList);
        req.setAttribute("activeService", sId);

        req.setAttribute("maxPage", maxPage);
        req.setAttribute("page", page);

        req.setAttribute("desc", desc);

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
