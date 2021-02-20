package app.service;

import app.entity.Account;
import app.entity.AccountService;
import app.factory.Impl.DMFactoryImpl;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class GetPayment {
    private static final Logger LOG = Logger.getLogger(GetPayment.class);

    public static void getPayment() {
        List<Account> accountList = DMFactoryImpl.getInstance().getAccountDM().getAllAccounts();
        for (Account account : accountList) {
            LOG.info("Processing account [" + account.getId() + "]");
            getPaymentFromAccount(account);
        }
    }

    private static void getPaymentFromAccount(Account account) {
        List<AccountService> linkedServices = DMFactoryImpl.getInstance().getServiceTariffDM().getAllAccountServices(account.getId());
        int servicesLinked = linkedServices.size();
        if (servicesLinked <= 0) {
            return;
        }
        Date now = Date.valueOf(LocalDate.now());
        for (AccountService acs : linkedServices) {
            if (!acs.isStatus() || !acs.isPayed() || acs.getNexPaymentDay() != now) {
                continue;
            }
            int tariffPrice = DMFactoryImpl.getInstance().getServiceTariffDM().getTariffById(acs.getTariffId()).getPrice();
            if (account.getMoneyBalance() >= tariffPrice) {
                account.setMoneyBalance(account.getMoneyBalance() - tariffPrice);
                Date nextPaymentDay = Date.valueOf(LocalDate.now().plusMonths(1));
                acs.setNexPaymentDay(nextPaymentDay);
            } else {
                servicesLinked--;
                acs.setPayed(false);
                acs.setStatus(false);
                acs.setPaymentAmount(tariffPrice);
            }
            DMFactoryImpl.getInstance().getServiceTariffDM().updateAccountService(acs);
        }
        if (servicesLinked <= 0) {
            account.setAccountStatus(false);
        }
        DMFactoryImpl.getInstance().getAccountDM().applyAccountData(account);
    }


    /*-------------------------------------------------------------------*/

    /**.
     * THIS BLOCK OF CODE WAS COPIED JUST FOR DEMONSTRATING FUNCTIONALITY OF
     * MONTH PAYMENT BY CLICKING ON BUTTON IN ADMIN PANEL
     *
     * DELETE THIS AFTER DEMONSTRATION!
     * */

    public static void getMonthPayment(int monthCount) {
        for (int i = 0; i < monthCount; i++) {
            List<Account> accountList = DMFactoryImpl.getInstance().getAccountDM().getAllAccounts();
            for (Account account : accountList) {
                LOG.info("Processing account [" + account.getId() + "]");
                getPaymentFromAccountUnsafe(account);
            }
        }
    }

    private static void getPaymentFromAccountUnsafe(Account account) {
        List<AccountService> linkedServices = DMFactoryImpl.getInstance().getServiceTariffDM().getAllAccountServices(account.getId());
        int servicesLinked = linkedServices.size();
        if (servicesLinked <= 0) {
            return;
        }
        Date now = Date.valueOf(LocalDate.now());
        for (AccountService acs : linkedServices) {
            if (!acs.isStatus() || !acs.isPayed()) {
                continue;
            }
            int tariffPrice = DMFactoryImpl.getInstance().getServiceTariffDM().getTariffById(acs.getTariffId()).getPrice();
            if (account.getMoneyBalance() >= tariffPrice) {
                account.setMoneyBalance(account.getMoneyBalance() - tariffPrice);
                Date nextPaymentDay = Date.valueOf(LocalDate.now().plusMonths(1));
                acs.setNexPaymentDay(nextPaymentDay);
            } else {
                servicesLinked--;
                acs.setPayed(false);
                acs.setStatus(false);
                acs.setPaymentAmount(tariffPrice);
            }
            DMFactoryImpl.getInstance().getServiceTariffDM().updateAccountService(acs);
        }
        if (servicesLinked <= 0) {
            account.setAccountStatus(false);
        }
        DMFactoryImpl.getInstance().getAccountDM().applyAccountData(account);
    }

}
