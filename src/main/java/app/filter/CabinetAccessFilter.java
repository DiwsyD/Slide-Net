package app.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/cabinet/admin/*", "/cabinet/user/*"})
public class CabinetAccessFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(CabinetAccessFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        /**.
         * Deny back button
         * */
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        httpResponse.setDateHeader("Expires", 0); // Proxies.

        try {
            if (req.getRequestURI().contains(session.getAttribute("role").toString())) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect("/cabinet");
            }
        } catch (NullPointerException npe) {
            LOG.debug("Cant get role, so redirecting to SignIn page");
            resp.sendRedirect("/sign");
        }
    }
}
