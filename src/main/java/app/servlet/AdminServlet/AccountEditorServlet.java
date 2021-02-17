package app.servlet.AdminServlet;

import app.entity.Account;
import app.service.AccountDataManager;
import app.service.Encryption;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cabinet/admin/account_mng/account_editor")
public class AccountEditorServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(AccountEditorServlet.class);

    protected void redirect(HttpServletRequest req, HttpServletResponse resp, String address) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher( address + ".jsp");
        requestDispatcher.forward(req, resp);
    }

    protected void getBackToAccountManager(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Redirecting to manager...");
        resp.sendRedirect( "/cabinet/admin/account_mng");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Account account = null;
            LOG.debug("Account id: " + req.getParameter("account_id"));
            if (req.getParameter("account_id") != null) {
                int account_id = Integer.parseInt(req.getParameter("account_id"));
                account = AccountDataManager.findAccountByIdOrNull(account_id);
            }
            if (account == null) {
                account = AccountDataManager.createNewAccount();
            }

            req.getSession().setAttribute("account", account);

        } catch (NumberFormatException e) {
            LOG.debug("Can't get account id!\n" +
                    "You may be trying to get access to this page via URL or with modified html.\n" +
                    "Please, use site navigation (without modification).");
        }
        redirect(req, resp, req.getRequestURI());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            if (req.getParameter("account_result") == null || !req.getParameter("account_result").equals("save")) {
                getBackToAccountManager(req, resp);
                return;
            }

            Account account = (Account) req.getSession().getAttribute("account");

            //Get account data from inputs
            String firstName = req.getParameter("first_name");
            String lastName = req.getParameter("last_name");
            String secondName = req.getParameter("second_name");

            LOG.debug("fName: " + firstName + "; lName: " + lastName + "; sName: " + secondName);

            String phoneNumber = req.getParameter("phone_number");
            String address = req.getParameter("address");
            String ipAddress = req.getParameter("ip_address");

            Integer moneyBalance = Integer.valueOf(req.getParameter("money_balance"));

            boolean accountStatus = Boolean.parseBoolean(req.getParameter("account_status"));

            //set account data to acccount object
            account.setfName(firstName);
            account.setlName(lastName);
            account.setsName(secondName);

            account.setPhoneNumber(phoneNumber);
            account.setAddress(address);
            account.setIpAddress(ipAddress);

            account.setMoneyBalance(moneyBalance);

            account.setAccountStatus(accountStatus);

            if (req.getParameter("new_password") != null && req.getParameter("new_password").trim().length() > 4) {
                String newPassword = req.getParameter("new_password").trim();
                account.setPassword(Encryption.encrypt(newPassword));
            }

            AccountDataManager.applyAccountData(account);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Wrong data from Input! Or Account is not exist!");
        }
        getBackToAccountManager(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(AccountDataManager.generatePassword(9));       // Write response body.
    }
}
