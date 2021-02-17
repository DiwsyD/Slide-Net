package app.dao;

import app.entity.Account;
import app.entity.AccountService;

import java.util.List;

public interface AccountDAO {
    Account getAccountById(long id);

    Account getAccountByLogin(long login);

    List<Account> getAccounts(int start, int end);

    List<Account> getAllAccounts();

    int getAccountCount();

    int getLastAccountId();

    void addAccount(Account account);

    void updateAccount(Account account);

    void activateServiceToAccount(AccountService accountService);

    void updateServiceToAccount(AccountService accountService);

    void disableServiceFromAccount(long accountId, int serviceId);

}
