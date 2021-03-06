package app.servlet;

import app.constants.Role;
import app.entityDataManager.impl.AccountDMImpl;
import app.factory.impl.DMFactoryImpl;
import app.entityDataManager.impl.ServiceTariffDMImpl;
import app.service.*;
import app.entity.Account;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        name = "AuthorizationServlet",
        urlPatterns = {"/sign"})
public class SignInOutServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ServiceTariffDMImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Enter to SignIn Page");
        language.checkLanguage(req, resp);
        HttpSession session = req.getSession();
        try {
            boolean signed = session != null
                    && session.getAttribute("login") != null
                    && session.getAttribute(Role.ROLE) != null;
            
            if(!signed) {
                req.getRequestDispatcher(req.getContextPath() + "/sign.jsp").forward(req, resp);
                return;
            }

            String signAction = req.getParameter("signAction");

            if (signAction.equals("out")) {
                LOG.info("=======Log Out=======");
                session.removeAttribute("login");
                session.removeAttribute(Role.ROLE);
                resp.sendRedirect(req.getContextPath() + "/sign");
            } else if (signAction.equals("in")) {
                LOG.info("Redirecting to private cabinet...");
                resp.sendRedirect(req.getContextPath() + "/cabinet");
            }
        } catch (NullPointerException npe) {
            LOG.debug("Sing Action is undefined! Returning to distribute servlet...");
            resp.sendRedirect(req.getContextPath() + "/cabinet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String userAuthPass = req.getParameter("pass");
        HttpSession session = req.getSession();

        AccountDMImpl accountDM = DMFactoryImpl.getInstance().getAccountDM();
        Account account = accountDM.findAccountByLoginOrNull(Validator.validateLogin(login));

        LOG.info("Validate User...");
        if (Validator.validateAccount(account, login, userAuthPass)) {
            LOG.info("Valid data.");
            LOG.info("=======Log In=======");

            session.setAttribute("login", account.getLogin());
            session.setAttribute("id", account.getId());
            session.setAttribute("role", account.getRoleName());

            resp.sendRedirect(req.getContextPath() + "/cabinet");
        } else {
            LOG.info("Invalid data.");
            req.setAttribute("AuthorizationResultError", true);
            this.doGet(req, resp);
        }
    }

}
