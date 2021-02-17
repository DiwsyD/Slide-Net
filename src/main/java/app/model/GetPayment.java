package app.model;

import app.entity.Account;
import app.entity.AccountService;
import app.entity.Tariff;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class GetPayment {

    public static void getMonthPayment(int monthCount) {
        List<Account> accountList = AccountDataManager.getAllAccounts();
        for (int i = 0; i < monthCount; i++) {
            for (Account account : accountList) {
                getPaymentFromAccount(account);
            }
        }
    }


    private static void getPaymentFromAccount(Account account) {
        List<AccountService> linkedServices = ServiceTariffDataManager.getAllAccountServices(account.getId());
        for (AccountService acs : linkedServices) {
            int tariffPrice = ServiceTariffDataManager.getTariffById(acs.getTariffId()).getPrice();
            if (account.getMoneyBalance() >= tariffPrice) {
                account.setMoneyBalance(account.getMoneyBalance() - tariffPrice);
                Date nextPaymentDay = Date.valueOf(LocalDate.now().plusMonths(1));
                acs.setNexPaymentDay(nextPaymentDay);
            } else {
                acs.setPayed(false);
                acs.setStatus(false);
                acs.setPaymentAmount(tariffPrice);
            }
            ServiceTariffDataManager.updateAccountService(acs);
        }
        AccountDataManager.applyAccountData(account);
    }

}
