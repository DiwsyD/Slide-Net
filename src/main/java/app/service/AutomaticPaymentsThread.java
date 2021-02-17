package app.service;

import org.apache.log4j.Logger;

public class AutomaticPaymentsThread extends Thread{
    private static final Logger LOG = Logger.getLogger(AutomaticPaymentsThread.class);

    //24 hours * 60 min * 60 sec * 1000 = one day in milliseconds
    private static long checkDelay = 86_400_000;
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(checkDelay);
                LOG.info("==Hour of reckoning!==");
                LOG.info("(Get payments from accounts)");
                GetPayment.getPayment();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
