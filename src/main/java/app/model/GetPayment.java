package app.model;

import app.entity.Account;
import app.entity.AccountService;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class GetPayment {
    private static final Logger LOG = Logger.getLogger(GetPayment.class);

    public static void getMonthPayment(int monthCount) {
        List<Account> accountList = AccountDataManager.getAllAccounts();
        LOG.debug(accountList.toString());
        for (int i = 0; i < monthCount; i++) {
            for (Account account : accountList) {
                getPaymentFromAccount(account);
            }
        }
    }

    private static void getPaymentFromAccount(Account account) {
        LOG.info("Processing account [" + account.getId() + "]");
        List<AccountService> linkedServices = ServiceTariffDataManager.getAllAccountServices(account.getId());
        int servicesLinked = linkedServices.size();
        if (servicesLinked <= 0) {
            return;
        }
        for (AccountService acs : linkedServices) {
            int tariffPrice = ServiceTariffDataManager.getTariffById(acs.getTariffId()).getPrice();
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
            ServiceTariffDataManager.updateAccountService(acs);
        }
        if (servicesLinked <= 0) {
            account.setAccountStatus(false);
        }
        AccountDataManager.applyAccountData(account);
    }

}
