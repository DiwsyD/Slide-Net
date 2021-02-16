package app.model;

import app.database.conf.ConstantQuery;
import app.database.dao.AccountUserDAO;
import app.entity.Account;
import app.entity.AccountService;
import app.entity.EntityManager;
import app.entity.Role;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountDataManager {

    private AccountDataManager() {}
    private static final Logger LOG = Logger.getLogger(AccountDataManager.class);

    private final static int maxAccountCount = 10_000_000;
    public static final int DAY_IN_MONTH = 30;

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

        account = AccountUserDAO.getInstance().getAccountById(id);
        if(account != null) {
            account.setActiveServices(ServiceTariffDataManager.getAllAccountServices(account.getId()));
            addAccountToEntityManager(setAccountRoleName(account));
        }
        return account;
    }

    public static Account findAccountByLoginOrNull(int login) {
        Account account = EntityManager.getInstance().getAccountByLogin(login);
        /*if (account != null) {
            return account;
        }*/

        account = AccountUserDAO.getInstance().getAccountByLogin(login);

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
            LOG.debug("Money: " + account.getMoneyBalance());
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

    public static void applyServiceToAccount(long accountId, long serviceId, long tariffId) {
        LOG.debug("Applying Service [" + serviceId + "] with tariff [" + tariffId + "] To Account [" + accountId + "]");
        AccountService linkedServices = ServiceTariffDataManager.getAccountService(accountId, serviceId);
        if (linkedServices != null) {
            LOG.debug("Service already activated, updating...");
            updateAccountService(linkedServices, tariffId);
        } else {
            LOG.debug("Service not activated yet, activating...");
            addAccountService(accountId, serviceId, tariffId);
        }
    }

    private static void addAccountService(long accountId, long serviceId, long tariffId) {
        Date activationDate = Date.valueOf(LocalDate.now());
        Date nextPaymentDay = Date.valueOf(LocalDate.now().plusMonths(1));
        LOG.debug("Activation day: " + activationDate + "; NPE: " + nextPaymentDay + ";");

        AccountService accountService = new AccountService();
        accountService.setAccountId(accountId);
        accountService.setServiceId(serviceId);
        accountService.setTariffId(tariffId);
        accountService.setActivationTime(activationDate);

        int tariffPrice = ServiceTariffDataManager.getTariffById(tariffId).getPrice();
        LOG.debug("New Tariff - " + ServiceTariffDataManager.getTariffById(tariffId).getName() + "; Price: " + tariffPrice);
        accountService.setStatus(false);
        accountService.setNexPaymentDay(nextPaymentDay);
        accountService.setPayed(false);
        accountService.setPaymentAmount(tariffPrice);
        AccountUserDAO.getInstance().activateServiceToAccount(accountService);
    }

    public static void pauseServiceOnAccount(long accountId, long serviceId) {
        AccountService linkedServices = ServiceTariffDataManager.getAccountService(accountId, serviceId);
        linkedServices.setStatus(false);
        System.out.println("isPayed?? >>" + linkedServices.isPayed());
        AccountUserDAO.getInstance().updateServiceToAccount(linkedServices);
    }

    public static void startServiceOnAccount(long accountId, long serviceId) {
        AccountService linkedServices = ServiceTariffDataManager.getAccountService(accountId, serviceId);
        if (!linkedServices.isPayed()) {
            LOG.debug("GetMoney: " + linkedServices.getPaymentAmount());
            //get money from account
            getPayForService(accountId, linkedServices.getPaymentAmount());

            Date activationDate = Date.valueOf(LocalDate.now());
            Date nextPaymentDay = Date.valueOf(LocalDate.now().plusMonths(1));

            linkedServices.setActivationTime(activationDate);
            linkedServices.setNexPaymentDay(nextPaymentDay);
            linkedServices.setPayed(true);
            linkedServices.setPaymentAmount(0);
            LOG.debug("Set payed!");
        }
        linkedServices.setStatus(true);
        System.out.println("isPayed?? >>" + linkedServices.isPayed());
        AccountUserDAO.getInstance().updateServiceToAccount(linkedServices);
    }

    private static void getPayForService(long accountId, int paymentAmount) {
        LOG.debug("I'll get + " + paymentAmount);
        Account account = findAccountByIdOrNull(accountId);
        account.setMoneyBalance(account.getMoneyBalance() - paymentAmount);
        AccountDataManager.applyAccountData(account);
    }

    private static void updateAccountService(AccountService linkedService, long tariffId) {
        if (!linkedService.isPayed()) {
            linkedService.setTariffId(tariffId);
            AccountUserDAO.getInstance().updateServiceToAccount(linkedService);
            return;
        }

        Date today = Date.valueOf(LocalDate.now());
        Date oldNextPaymentDay = linkedService.getNexPaymentDay();
        Date nextPaymentDay = Date.valueOf(LocalDate.now().plusMonths(1));

        LOG.debug("Activation day: " + today + "; OldNPE: "  + oldNextPaymentDay + "; NPE: " + nextPaymentDay + ";");

        int differenceDays = (int) ChronoUnit.DAYS.between(today.toLocalDate(), oldNextPaymentDay.toLocalDate());
        int oldTariffPrice = ServiceTariffDataManager.getTariffById(linkedService.getTariffId()).getPrice();
        int newTariffPrice = ServiceTariffDataManager.getTariffById(tariffId).getPrice() ;

        LOG.debug("To next payment: " + differenceDays);
        LOG.debug("Price OLD tariff: " + oldTariffPrice);
        LOG.debug("Price NEW tariff: " + newTariffPrice);

        int diffPrice = (int) (newTariffPrice - (differenceDays * ((double)oldTariffPrice / DAY_IN_MONTH)));

        LOG.debug("Price for new Tariff: " + diffPrice);

        int paymentForUpdatedTariff = Math.max(diffPrice, 0);

        linkedService.setTariffId(tariffId);
        linkedService.setActivationTime(today);
        linkedService.setNexPaymentDay(nextPaymentDay);
        linkedService.setStatus(false);
        linkedService.setPayed(false);
        linkedService.setPaymentAmount(paymentForUpdatedTariff);

        AccountUserDAO.getInstance().updateServiceToAccount(linkedService);
    }

    public static void disableService(long id, int serviceId) {
        LOG.debug("Disabling...");
        AccountUserDAO.getInstance().disableServiceFromAccount(id, serviceId);
    }
}


























