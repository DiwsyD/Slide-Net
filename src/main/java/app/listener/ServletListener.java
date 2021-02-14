package app.listener;

import app.entity.Service;
import app.model.ServiceTariffDataManager;
import app.servlet.AdminServlet.AdminAccountServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

@WebListener
public class ServletListener implements HttpSessionListener {
    private static final Logger LOG = Logger.getLogger(AdminAccountServlet.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        String path = context.getContextPath();

        context.setAttribute("app", path);
    }




}
