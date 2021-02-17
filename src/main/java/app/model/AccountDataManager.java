package app.model;

import app.dao.DAOImpl.AccountDAOImpl;
import app.entity.Account;
import app.entity.AccountService;
import app.entity.EntityManager;
import app.entity.Role;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
        /*if (account != null) {
            LOG.debug("FOUND IN ==> ENTITY MANAGER ACCOUNT ID: " + account.getId() + "; Name: " + account.getfName());
            return account;
        }*/

        account = AccountDAOImpl.getInstance().getAccountById(id);
        if(account != null) {
            account.setActiveServices(ServiceTariffDataManager.getAllAccountServices(account.getId()));
            addAccountToEntityManager(setAccountRoleName(account));
        }
        return account;
    }

    public static Account findAccountByLoginOrNull(long login) {
        Account account = EntityManager.getInstance().getAccountByLogin(login);
        /*if (account != null) {
            return account;
        }*/

        account = AccountDAOImpl.getInstance().getAccountByLogin(login);

        if(account != null) {
            account.setActiveServices(ServiceTariffDataManager.getAllAccountServices(account.getId()));
            addAccountToEntityManager(setAccountRoleName(account));
        }
        return account;
    }

    //--------------

    public static List<Account> getAccounts(int page, int pageSize) {
        List<Account> accountList = new ArrayList<>();
        int accountsToGet = (page - 1) * pageSize;
        for (Account account : AccountDAOImpl.getInstance().getAccounts(pageSize, accountsToGet)) {
            accountList.add(setAccountRoleName(account));
        }
        return accountList;
    }

    public static List<Account> getAllAccounts() {
        return AccountDAOImpl.getInstance().getAllAccounts();
    }

    public static int getAccountCount() {
        return AccountDAOImpl.getInstance().getAccountCount();
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
            LOG.debug("Money: " + account.getMoneyBalance());
            EntityManager.getInstance().updateAccount(account);
            AccountDAOImpl.getInstance().updateAccount(account);
            return;
        }
        account.setPassword(Encryption.encrypt(account.getPassword()));
        EntityManager.getInstance().addAccount(account);
        AccountDAOImpl.getInstance().addAccount(account);
    }

    //Updaters

    public static boolean changePassword(long account_id, String oldPass, String newPass, String newPassRepeat) {
        Account account = findAccountByIdOrNull(account_id);
        if (!account.getPassword().equals(Encryption.encrypt(oldPass))
                || oldPass.equals(newPass) || oldPass.equals(newPassRepeat)
                || !newPass.equals(newPassRepeat) || !Validator.validatePassword(newPass)) {
            return false;
        }
        account.setPassword(Encryption.encrypt(newPass));
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

        account.setId((long) (AccountDAOImpl.getInstance().getLastAccountId() + 1));
        account.setRoleId(userRole.getId());
        account.setRoleName(userRole.getName());
        account.setLogin(generateNewLogin());
        account.setPassword(generatePassword(9));
        account.setMoneyBalance(0);

        return account;
    }

    //Generate New Account login
    public static int generateNewLogin() {
        int accountCount = AccountDAOImpl.getInstance().getLastAccountId();
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

    public static void applyServiceToAccount(long accountId, long serviceId, long tariffId) {
        LOG.debug("Applying Service [" + serviceId + "] with tariff [" + tariffId + "] To Account [" + accountId + "]");
        AccountService linkedServices = ServiceTariffDataManager.getAccountService(accountId, serviceId);
        if (linkedServices != null) {
            LOG.debug("Service already activated, updating...");
            ServiceTariffDataManager.updateTariffAccountService(linkedServices, tariffId);
        } else {
            LOG.debug("Service not activated yet, activating...");
            addAccountService(accountId, serviceId, tariffId);
        }
    }

    private static void addAccountService(long accountId, long serviceId, long tariffId) {
        Date activationDate = Date.valueOf(LocalDate.now());
        AccountService accountService = new AccountService();
        accountService.setAccountId(accountId);
        accountService.setServiceId(serviceId);
        accountService.setTariffId(tariffId);
        accountService.setStatus(false);
        accountService.setPayed(false);
        accountService.setActivationTime(activationDate);
        int tariffPrice = ServiceTariffDataManager.getTariffById(tariffId).getPrice();
        accountService.setPaymentAmount(tariffPrice);
        LOG.debug("New Tariff - " + ServiceTariffDataManager.getTariffById(tariffId).getName() + "; Price: " + tariffPrice);
        AccountDAOImpl.getInstance().activateServiceToAccount(accountService);
    }

    public static void pauseServiceOnAccount(long accountId, long serviceId) {
        AccountService linkedServices = ServiceTariffDataManager.getAccountService(accountId, serviceId);
        linkedServices.setStatus(false);
        System.out.println("isPayed?? >>" + linkedServices.isPayed());
        AccountDAOImpl.getInstance().updateServiceToAccount(linkedServices);
    }

    public static void startServiceOnAccount(long accountId, long serviceId) {
        AccountService linkedServices = ServiceTariffDataManager.getAccountService(accountId, serviceId);
        if (!linkedServices.isPayed()) {
            LOG.debug("GetMoney: " + linkedServices.getPaymentAmount());
            //get money from account
            getPayForService(accountId, linkedServices.getPaymentAmount());

            Date nextPaymentDay = Date.valueOf(LocalDate.now().plusMonths(1));

            linkedServices.setNexPaymentDay(nextPaymentDay);
            linkedServices.setPayed(true);
            linkedServices.setPaymentAmount(0);
            LOG.debug("Set payed!");
        }
        linkedServices.setStatus(true);

        Account account = findAccountByIdOrNull(accountId);
        if (!account.isAccountStatus()) {
            account.setAccountStatus(true);
            AccountDataManager.applyAccountData(account);
        }
        System.out.println("isPayed?? >>" + linkedServices.isPayed());
        AccountDAOImpl.getInstance().updateServiceToAccount(linkedServices);
    }

    private static void getPayForService(long accountId, int paymentAmount) {
        LOG.debug("I'll get + " + paymentAmount);
        Account account = findAccountByIdOrNull(accountId);
        account.setMoneyBalance(account.getMoneyBalance() - paymentAmount);
        AccountDataManager.applyAccountData(account);
    }

    public static void disableService(long accountId, int serviceId) {
        LOG.debug("Disabling...");
        AccountDAOImpl.getInstance().disableServiceFromAccount(accountId, serviceId);
        int linkedServices = ServiceTariffDataManager.getActiveAccountServiceCount(accountId);
        if (linkedServices <= 0) {
            Account account = findAccountByIdOrNull(accountId);
            account.setAccountStatus(false);
            AccountDataManager.applyAccountData(account);
        }
    }
}


























