package app.servlet.UserServlet;

import app.entity.Account;
import app.entity.AccountService;
import app.entity.Service;
import app.model.AccountDataManager;
import app.model.ServiceTariffDataManager;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
        "/cabinet/user/user_cabinet",
        "/cabinet/user/topup_balance",
        "/cabinet/user/change_password"
})
public class AccountServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(AccountServlet.class);

    protected void redirectToCabinet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher( "/cabinet/user/user_cabinet.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Check if we want to activate or add service
        if (req.getParameter("activate") != null || req.getParameter("edit") != null) {
            activateEditService(req, resp);
            return;
        }
        if (req.getParameter("disable") != null) {
            disableService(req);
        }

        List<Service> serviceList = ServiceTariffDataManager.getAllServices();
        long accountId = (long) req.getSession().getAttribute("id");
        List<AccountService> linkedServices = ServiceTariffDataManager.getAccountServices(accountId);
        Account acc = (Account) req.getSession().getAttribute("account_data");
        acc.setActiveServices(linkedServices);


        req.setAttribute("activeServices", linkedServices);
        req.setAttribute("serviceList", serviceList);
        req.getRequestDispatcher(req.getRequestURI() + ".jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().contains("topup_balance")) {
            topUpBalance(req);
        }
        if (req.getRequestURI().contains("change_password")) {
            changePassword(req, resp);
        }
        redirectToCabinet(req, resp);
    }
    protected void changePassword(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        try {
            long id = (long) session.getAttribute("id");
            String oldPass = req.getParameter("oldPass");
            String newPass = req.getParameter("newPass");
            String newPassRepeat = req.getParameter("newPassRepeat");
            LOG.debug("oldPass = " + oldPass + "; newPass = " + newPass + "; newPassRepeat = " + newPassRepeat);
            if (!AccountDataManager.changePassword(id, oldPass, newPass, newPassRepeat)) {
                session.setAttribute("changePasswordError", true);
                this.doGet(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("changePasswordError", true);
            LogLog.warn("=Wrong Change Password Data!=");
        }
    }

    protected void topUpBalance(HttpServletRequest req) {
        HttpSession session = req.getSession();
        try {
            long id = (long) session.getAttribute("id");
            int amount = Integer.parseInt(req.getParameter("topup_amount"));
            AccountDataManager.topUpBalance(id, amount);
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("topup_error", true);
            LogLog.warn("=Wrong Top Up Data!=");
        }
    }

    private void activateEditService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.info("Redirecting to Service Choice Page");
        StringBuilder path = new StringBuilder(req.getRequestURI());
        if (req.getParameter("activate") != null) {
            LOG.debug("Activate!");
            path.append("/select_tariff?activate=true")
                    .append("&serviceId=").append(req.getParameter("serviceId"));
        }
        if (req.getParameter("edit") != null) {
            LOG.debug("Edit!");
            path.append("/select_tariff?edit=true")
                    .append("serviceId=").append(req.getParameter("serviceId"))
                    .append("tariffId=").append(req.getParameter("tariffId"));
        }
        resp.sendRedirect(path.toString());
        return;
    }

    private void disableService(HttpServletRequest req) {
        HttpSession session = req.getSession();
        try {
            long id = (long) session.getAttribute("id");
            int serviceId = Integer.parseInt(req.getParameter("serviceId"));
            AccountDataManager.disableService(id, serviceId);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("=Wrong Disable Action!=");
        }
    }


}
/*





* */