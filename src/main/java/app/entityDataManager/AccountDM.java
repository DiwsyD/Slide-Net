package app.entityDataManager;

import app.entity.Account;

import java.util.List;

public interface AccountDM {

    Account findAccountByIdOrNull(long id);

    Account findAccountByLoginOrNull(long login);

    List<Account> getAccounts(int page, int pageSize);

    List<Account> getAllAccounts();

    int getAccountCount();

    void applyAccountData(Account account);

    boolean changePassword(long account_id, String oldPass, String newPass, String newPassRepeat);

    void topUpBalance(long account_id, int amount);

    //Create new account
    Account createNewAccount();

    //Generate New Account login
    int generateNewLogin();

    //Generate new password
    String generatePassword(int length);

    void applyServiceToAccount(long accountId, long serviceId, long tariffId);

    void pauseServiceOnAccount(long accountId, long serviceId);

    void startServiceOnAccount(long accountId, long serviceId);

    void disableService(long accountId, int serviceId);






























}
