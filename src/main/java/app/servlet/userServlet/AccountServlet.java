package app.servlet.userServlet;

import app.entity.Account;
import app.entity.AccountService;
import app.entity.Service;
import app.factory.impl.DMFactoryImpl;
import app.service.language;
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
        resp.sendRedirect( req.getContextPath() + "/cabinet/user/user_cabinet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        language.checkLanguage(req, resp);
        //Check if we want to activate or add service
        if (req.getParameter("action") != null) {
            serviceAction(req, resp);
            return;
        }
        List<Service> serviceList = DMFactoryImpl.getInstance().getServiceTariffDM().getAllServices();

        //Update AccountData
        HttpSession session = req.getSession();
        long accountId = (long) session.getAttribute("id");
        Account account = DMFactoryImpl.getInstance().getAccountDM().findAccountByIdOrNull(accountId);
        session.setAttribute("account_data", account);
        List<AccountService> linkedServices = account.getActiveServices();

        req.setAttribute("activeServices", linkedServices);
        req.setAttribute("serviceList", serviceList);

        req.getRequestDispatcher(req.getRequestURI() + ".jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Post action!");
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
            if (!DMFactoryImpl.getInstance().getAccountDM().changePassword(id, oldPass, newPass, newPassRepeat)) {
                LOG.warn("=Invalid password format!=");
                session.setAttribute("changePasswordError", true);
                this.doGet(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("changePasswordError", true);
            LOG.warn("=Wrong Change Password Data!=");
        }
    }

    protected void topUpBalance(HttpServletRequest req) {
        HttpSession session = req.getSession();
        try {
            long id = (long) session.getAttribute("id");
            int amount = Integer.parseInt(req.getParameter("topup_amount"));
            DMFactoryImpl.getInstance().getAccountDM().topUpBalance(id, amount);
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("topup_error", true);
            LogLog.warn("=Wrong Top Up Data!=");
        }
    }

    private void serviceAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.info("Redirecting to Service Choice Page");
        String action = req.getParameter("action");
        if (action == null) {
            return;
        }
        long id = -1;
        int serviceId = -1;
        try {
            id = (long) req.getSession().getAttribute("id");
            serviceId = Integer.parseInt(req.getParameter("serviceId"));
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("=Wrong Disable Action!=");
        }
        StringBuilder path = new StringBuilder(req.getRequestURI());

        if (action.equals("activate")) {
            LOG.info("Activate action...");
            path.append("/select_tariff?action=activate")
                    .append("&serviceId=").append(req.getParameter("serviceId"));
        }
        if (action.equals("edit")) {
            LOG.info("Edit action...");
            path.append("/select_tariff?action=edit")
                    .append("&serviceId=").append(req.getParameter("serviceId"))
                    .append("&tariffId=").append(req.getParameter("tariffId"));
        }
        if (action.equals("disable")) {
            DMFactoryImpl.getInstance().getAccountDM().disableService(id, serviceId);
        }
        if (action.equals("pause")) {
            DMFactoryImpl.getInstance().getAccountDM().pauseServiceOnAccount(id, serviceId);
        }
        if (action.equals("start")) {
            DMFactoryImpl.getInstance().getAccountDM().startServiceOnAccount(id, serviceId);
        }
        resp.sendRedirect(path.toString());
    }

}