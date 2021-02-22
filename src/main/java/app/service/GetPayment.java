package app.service;

import app.entity.Account;
import app.entity.AccountService;
import app.entityDataManager.impl.AccountDMImpl;
import app.entityDataManager.impl.ServiceTariffDMImpl;
import app.factory.impl.DMFactoryImpl;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class GetPayment {
    private static final Logger LOG = Logger.getLogger(GetPayment.class);

    //For each Account - get payment
    public static void getPayment() {
        List<Account> accountList = DMFactoryImpl.getInstance().getAccountDM().getAllAccounts();
        for (Account account : accountList) {
            LOG.info("Processing account [" + account.getId() + "]");
            getPaymentFromAccount(account);
        }
    }

    /**.
     * 1. Get account
     * 2. Get all account linked services
     * 3. If time to pay for this service\tariff - subtract money from account balance
     * 4. Update Account Data.
     * */
    private static void getPaymentFromAccount(Account account) {
        ServiceTariffDMImpl serviceTariffDM = DMFactoryImpl.getInstance().getServiceTariffDM();
        List<AccountService> linkedServices = serviceTariffDM.getAllAccountServices(account.getId());
        int servicesLinked = (int) linkedServices.stream().filter(AccountService::isStatus).count();
        Date now = Date.valueOf(LocalDate.now());
        for (AccountService acs : linkedServices) {
            if (!acs.isStatus() || !acs.isPayed() || acs.getNexPaymentDay() != now) {
                continue;
            }
            int tariffPrice = serviceTariffDM.getTariffById(acs.getTariffId()).getPrice();
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
            serviceTariffDM.updateAccountService(acs);
        }
        if (servicesLinked < 1) {
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
     *
     * This methods do the same as methods above (without check payment date on each tariff)
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
        ServiceTariffDMImpl serviceTariffDM = DMFactoryImpl.getInstance().getServiceTariffDM();
        List<AccountService> linkedServices = serviceTariffDM.getAllAccountServices(account.getId());
        int servicesLinked = (int) linkedServices.stream().filter(AccountService::isStatus).count();
        for (AccountService acs : linkedServices) {
            if (!acs.isStatus() || !acs.isPayed()) {
                continue;
            }
            int tariffPrice = serviceTariffDM.getTariffById(acs.getTariffId()).getPrice();
            LOG.debug("Price:" + tariffPrice);
            if (account.getMoneyBalance() >= tariffPrice) {
                account.setMoneyBalance(account.getMoneyBalance() - tariffPrice);
                Date nextPaymentDay = Date.valueOf(LocalDate.now().plusMonths(1));
                acs.setNexPaymentDay(nextPaymentDay);
            } else {
                LOG.debug("DISABLE SERVICE: " + acs.getServiceId() + " : " + acs.getTariffId());
                servicesLinked--;
                acs.setPayed(false);
                acs.setStatus(false);
                acs.setPaymentAmount(tariffPrice);
            }
            serviceTariffDM.updateAccountService(acs);
        }

        LOG.debug("Account Name: [" + account.getfName() + "]");
        LOG.debug("Linked Services: [" + servicesLinked + "]");
        if (servicesLinked <= 0) {
            account.setAccountStatus(false);
        }
        AccountDMImpl accountDM = DMFactoryImpl.getInstance().getAccountDM();
        accountDM.applyAccountData(account);
    }

}
