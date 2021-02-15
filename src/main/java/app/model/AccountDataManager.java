package app.model;

import app.database.dao.AccountUserDAO;
import app.entity.Account;
import app.entity.AccountService;
import app.entity.EntityManager;
import app.entity.Role;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class AccountDataManager {

    private AccountDataManager() {}
    private static final Logger LOG = Logger.getLogger(AccountDataManager.class);

    private final static int maxAccountCount = 10_000_000;

    /**.
     * Description to all methods.
     *
     * Main algorithm in all methods:
     *
     * Try to get *object* from local storage
     * if local storage is not contains *object*
     *  - try to get *object* from DB
     *
     * [If we gets *object* from DB - add it to local storage]
     * */


    public static Account findAccountByIdOrNull(long id) {
        Account account = EntityManager.getInstance().getAccountById(id);
        if (account != null) {
            LOG.debug("FOUND IN ==> ENTITY MANAGER ACCOUNT ID: " + account.getId() + "; Name: " + account.getfName());
            return account;
        }

        account = AccountUserDAO.getInstance().getAccountById(id);
        if(account != null) {
            addAccountToEntityManager(setAccountRoleName(account));
        }
        return account;
    }

    public static Account findAccountByLoginOrNull(int login) {
        Account account = EntityManager.getInstance().getAccountByLogin(login);
        if (account != null) {
            return account;
        }


        account = AccountUserDAO.getInstance().getAccountByLogin(login);
        if(account != null) {
            addAccountToEntityManager(setAccountRoleName(account));
        }
        return account;
    }

    //--------------

    public static List<Account> getAccounts(int page, int pageSize) {
        List<Account> accountList = new ArrayList<>();
        int accountsToGet = (page - 1) * pageSize;
        for (Account account : AccountUserDAO.getInstance().getAccounts(pageSize, accountsToGet)) {
            accountList.add(setAccountRoleName(account));
        }
        return accountList;
    }

    public static int getAccountCount() {
        return AccountUserDAO.getInstance().getAccountCount();
    }

    //Setters
    private static Account setAccountRoleName(Account account) {
        account.setRoleName(RoleDataManager.getRoleById(account.getRoleId()).getName());
        return account;
    }

    //Adders

    //Add Account to local storage
    private static void addAccountToEntityManager(Account account) {
        EntityManager.getInstance().addAccount(account);
    }

    public static void applyAccountData(Account account) {
        if (findAccountByIdOrNull(account.getId()) != null) {
            account.setPassword(Encryption.encrypt(account.getPassword()));
            EntityManager.getInstance().updateAccount(account);
            AccountUserDAO.getInstance().updateAccount(account);
            return;
        }
        account.setPassword(Encryption.encrypt(account.getPassword()));
        EntityManager.getInstance().addAccount(account);
        AccountUserDAO.getInstance().addAccount(account);
    }

    //Updaters

    public static boolean changePassword(long account_id, String oldPass, String newPass, String newPassRepeat) {
        Account account = findAccountByIdOrNull(account_id);
        if (!account.getPassword().equals(oldPass)
                || oldPass.equals(newPass) || oldPass.equals(newPassRepeat)
                || !newPass.equals(newPassRepeat)) {
            return false;
        }
        account.setPassword(newPass);
        applyAccountData(account);
        return true;
    }

    public static void topUpBalance(long account_id, int amount) {
        if (amount < 20) {
            return;
        }
        Account account = findAccountByIdOrNull(account_id);
        account.setMoneyBalance(account.getMoneyBalance()+amount);
        applyAccountData(account);
    }

    //Generators

    //Create new account
    public static Account createNewAccount() {
        Account account = new Account();
        Role userRole = RoleDataManager.getRoleByName("user");

        account.setId((long) (AccountUserDAO.getInstance().getLastAccountId() + 1));
        account.setRoleId(userRole.getId());
        account.setRoleName(userRole.getName());
        account.setLogin(generateNewLogin());
        account.setPassword(generatePassword(9));

        return account;
    }

    //Generate New Account login
    public static int generateNewLogin() {
        int accountCount = AccountUserDAO.getInstance().getLastAccountId();
        int newId = maxAccountCount - (accountCount + 1);
        StringBuilder newLogin = new StringBuilder(String.valueOf(newId)).reverse();
        int nullsToAdd = String.valueOf(maxAccountCount).length() - String.valueOf(newLogin).length();
        for (int i = 0; i < nullsToAdd; i++) {
            newLogin.append(0);
        }
        return Integer.parseInt(newLogin.toString());
    }

    //Generate new password
    public static String generatePassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        for(int i = 0; i< length ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }

        return String.valueOf(password);
    }

    public static void activateService(long id, long serviceId, long tariffId) {
        Account account = findAccountByIdOrNull(id);

        Date activationDate = Date.valueOf(LocalDate.now());
        Date nextPaymentDay = Date.valueOf(LocalDate.now().plusMonths(1));

        AccountService accountService = new AccountService();
        accountService.setAccountId(id);
        accountService.setServiceId(serviceId);
        accountService.setTariffId(tariffId);
        accountService.setActivationTime(activationDate);
        accountService.setStatus(false);
        accountService.setNexPaymentDay(nextPaymentDay);
        AccountUserDAO.getInstance().activateServiceToAccount(accountService);
    }

    public static void disableService(long id, int serviceId) {
        AccountUserDAO.getInstance().disableServiceFromAccount(id, serviceId);
    }
}
