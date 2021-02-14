package app.servlet.UserServlet;

import app.entity.Account;
import app.entity.AccountService;
import app.entity.Service;
import app.model.AccountUserDataManager;
import app.model.ServiceTariffDataManager;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;

import javax.servlet.RequestDispatcher;
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
public class UserAccountServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(UserAccountServlet.class);

    protected void redirectToCabinet(HttpServletRequest req, HttpServletResponse resp, String address) throws ServletException, IOException {
        req.getRequestDispatcher( "/cabinet/user/user_cabinet.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Get");
        List<Service> serviceList = ServiceTariffDataManager.getAllServices();
        long accountId = (long) req.getSession().getAttribute("id");
        List<AccountService> linkedServices = ServiceTariffDataManager.getAccountServices(accountId);

        Account acc = (Account) req.getSession().getAttribute("account_data");

        acc.setActiveServices(linkedServices);

        req.setAttribute("activeServices", linkedServices);
        req.setAttribute("serviceList", serviceList);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(req.getRequestURI() + ".jsp");
        requestDispatcher.forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().contains("topup_balance")) {
            topUpBalance(req, resp);
        }
        if (req.getRequestURI().contains("change_password")) {
            changePassword(req, resp);
        }
        if (req.getParameter("save") != null) {

        }

        redirectToCabinet(req, resp, req.getRequestURI());
    }

    protected void changePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            long id = (long) session.getAttribute("id");
            String oldPass = req.getParameter("oldPass");
            String newPass = req.getParameter("newPass");
            String newPassRepeat = req.getParameter("newPassRepeat");
            LOG.debug("oldPass = " + oldPass + "; newPass = " + newPass + "; newPassRepeat = " + newPassRepeat);
            if (!AccountUserDataManager.changePassword(id, oldPass, newPass, newPassRepeat)) {
                session.setAttribute("changePasswordError", true);
                this.doGet(req, resp);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("changePasswordError", true);
            LogLog.warn("=Wrong Change Password Data!=");
        }
    }

    protected void topUpBalance(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            long id = (long) session.getAttribute("id");
            int amount = Integer.parseInt(req.getParameter("topup_amount"));
            AccountUserDataManager.topUpBalance(id, amount);
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("topup_error", true);
            LogLog.warn("=Wrong Top Up Data!=");
        }
    }


}
