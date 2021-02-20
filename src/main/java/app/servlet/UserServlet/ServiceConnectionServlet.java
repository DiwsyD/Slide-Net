package app.servlet.UserServlet;

import app.entity.Service;
import app.entityDataManager.Impl.DMFactoryImpl;
import app.service.ServiceTable;
import app.service.language;
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

    private void redirectToCabinet (HttpServletResponse resp) throws IOException {
        resp.sendRedirect( "/cabinet/user/user_cabinet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        language.checkLanguage(req, resp);
        //services and tariffs that account has
        try {
            LOG.info("Load Service Table...");
            int serviceId = Integer.parseInt(req.getParameter("serviceId"));
            Service service = DMFactoryImpl.getInstance().getServiceTariffDM().getServiceById(serviceId);
            if (service == null) {
                redirectToCabinet(resp);
                return;
            }
            List<Service> serviceList = Collections.singletonList(service);
            ServiceTable.loadServiceTable(req, serviceList);
        } catch (Exception e) {
            LOG.error("Error value of >serviceId<!");
            e.printStackTrace();
        }
        req.getRequestDispatcher(req.getRequestURI() + ".jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Tariff selected: " + req.getParameter("selectedTariff"));
        HttpSession session = req.getSession();
        try {
            long id = (long) session.getAttribute("id");
            long serviceId = Integer.parseInt(req.getParameter("serviceId"));
            long tariffId = Integer.parseInt(req.getParameter("selectedTariff"));
            DMFactoryImpl.getInstance().getAccountDM().applyServiceToAccount(id, serviceId, tariffId);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("=Wrong Disable Action!=");
        }
        LOG.debug("redirecting to cabinet");
        redirectToCabinet(resp);
    }
}
