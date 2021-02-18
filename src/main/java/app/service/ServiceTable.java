package app.service;

import app.entity.Service;
import app.entity.Tariff;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ServiceTable {
    private static final Logger LOG = Logger.getLogger(ServiceTable.class);

    public static void loadServiceTable(HttpServletRequest req, HttpServletResponse resp, List<Service> serviceList) {
        String orderBy = req.getParameter("orderBy");
        String desc = "";
        long serviceId = 1;

        if (req.getParameter("serviceId") != null) {
            serviceId = Integer.parseInt(req.getParameter("serviceId"));
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
        int maxPage = (int)Math.ceil((double) ServiceTariffDataManager.getServiceTariffCount(serviceId) / pagePaginSize);

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

        List<Tariff> tariffList =  ServiceTariffDataManager.getCertainServiceTariffs(serviceId, page, pagePaginSize, orderBy, desc);

        req.setAttribute("serviceList", serviceList);
        req.setAttribute("tariffList", tariffList);
        req.setAttribute("activeService", serviceId);

        req.setAttribute("maxPage", maxPage);
        req.setAttribute("page", page);

        req.setAttribute("desc", desc);
        req.setAttribute("uri", req.getRequestURI());

    }
}
