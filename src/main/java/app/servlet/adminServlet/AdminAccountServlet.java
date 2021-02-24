package app.servlet.adminServlet;

import app.entity.Account;
import app.entityDataManager.impl.AccountDMImpl;
import app.entityDataManager.impl.ServiceTariffDMImpl;
import app.factory.impl.DMFactoryImpl;
import app.service.GetPayment;
import app.service.language;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns =
        {"/cabinet/admin/admin_cabinet",
         "/cabinet/admin/settings"})
public class AdminAccountServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(AdminAccountServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        language.checkLanguage(req, resp);
        LOG.debug("doGet: " + req.getRequestURI());

        ServiceTariffDMImpl serviceTariffDM = DMFactoryImpl.getInstance().getServiceTariffDM();
        AccountDMImpl accountDM = DMFactoryImpl.getInstance().getAccountDM();

        int serviceCount = serviceTariffDM.getServiceCount();
        int tariffCount = serviceTariffDM.getTariffCount();
        int accountCount = accountDM.getAccountCount();

        showAccountServicesData(req, serviceTariffDM);

        req.setAttribute("serviceCount", serviceCount);
        req.setAttribute("tariffCount", tariffCount);
        req.setAttribute("accountCount", accountCount);
        req.getRequestDispatcher(req.getRequestURI() + ".jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int monthAccelerate = Integer.parseInt(req.getParameter("month"));
            monthAccelerate = Math.max(monthAccelerate, 1);
            LOG.debug("monthAccelerate = " + monthAccelerate);
            GetPayment.getMonthPayment(monthAccelerate);
        } catch (NumberFormatException e) {
            LOG.warn("=Invalid month accelerate number!=");
            e.printStackTrace();
        }
        resp.sendRedirect( req.getContextPath() + "/cabinet/admin/admin_cabinet");
    }

    private void showAccountServicesData(HttpServletRequest req, ServiceTariffDMImpl serviceTariffDM) {
        String triggerInfo = req.getParameter("showAccServData");
        if (!Boolean.parseBoolean(triggerInfo)) {
            return;
        }

        Map<Account, Integer> accountServices = serviceTariffDM.getAccountsServices();

        req.setAttribute("accServDataActive", true);
        req.setAttribute("accountServices", accountServices.entrySet());
    }
}