package app.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/cabinet")
public class DistributeServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(DistributeServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        resp.setDateHeader("Expires", 0); // Proxies.

        StringBuilder redirectTo = new StringBuilder();
        try {
            HttpSession session = req.getSession();
            String role = session.getAttribute("role").toString();
            LOG.info("Get account role: <" + role + ">");
            switch (role) {
                case "user":
                    redirectTo.append(req.getRequestURI()).append("/user/user_cabinet");
                    break;
                case "admin":
                    redirectTo.append(req.getRequestURI()).append("/admin/admin_cabinet");
                    break;
            }
        } catch (NullPointerException npe) {
            LOG.error("Error: Can't find account role!");
            redirectTo.append("/sign");
        } finally {
            LOG.info("redirecting to " + redirectTo.toString());
            resp.sendRedirect(redirectTo.toString());
        }
    }
}
