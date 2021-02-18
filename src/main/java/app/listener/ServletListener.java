package app.listener;

import app.service.AutomaticPaymentsThread;
import app.servlet.AdminServlet.AdminAccountServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletListener implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(AdminAccountServlet.class);
    private Thread automaticPayments = null;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("===>Slide-Net App started!<===");
        Thread automaticPayments = new AutomaticPaymentsThread();
        automaticPayments.start();
        LOG.info("===>Automatic Payment Thread started!<===");
        LOG.info("===>Checking cookie for language settings<===");
    }

    private void checkLanguage() {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (automaticPayments != null && !automaticPayments.isInterrupted()) {
            automaticPayments.interrupt();
        }
    }
}
