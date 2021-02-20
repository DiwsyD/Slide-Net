package app.servlet.AdminServlet;

import app.entity.Account;
import app.entityDataManager.Impl.DMFactoryImpl;
import app.service.language;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cabinet/admin/account_mng")
public class AccountManagerServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(AccountManagerServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        language.checkLanguage(req, resp);
        int page = 1;
        int pagePaginSize = 5;
        int maxPage = (int)Math.ceil((double) DMFactoryImpl.getInstance().getAccountDM().getAccountCount() / pagePaginSize);

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


        List<Account> accountList = DMFactoryImpl.getInstance().getAccountDM().getAccounts(page, pagePaginSize);

        req.setAttribute("accountList", accountList);

        req.setAttribute("maxPage", maxPage);
        req.setAttribute("page", page);
        req.setAttribute("uri", req.getRequestURI());

        req.getRequestDispatcher(req.getRequestURI() + ".jsp").forward(req, resp);
    }

}



