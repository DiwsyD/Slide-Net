package app.entityDataManager.Impl;

import app.dao.Impl.AccountDAOImpl;
import app.entity.Account;
import app.entity.AccountService;
import app.entity.Role;
import app.factory.Impl.DAOFactoryImpl;
import app.factory.Impl.DMFactoryImpl;
import app.service.Encryption;
import app.service.Validator;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountDMImpl {

    private static final Logger LOG = Logger.getLogger(AccountDMImpl.class);

    private final static int maxAccountCount = 10_000_000;

    public Account findAccountByIdOrNull(long id) {
        return finalizeAccount(getAccountDAO().getAccountById(id));
    }

    public Account findAccountByLoginOrNull(long login) {
        return finalizeAccount(getAccountDAO().getAccountByLogin(login));
    }

    /* Set role name and linked services to Account */
    private Account finalizeAccount(Account account) {
        if(account != null) {
            RoleDMImpl roleDM = DMFactoryImpl.getInstance().getRoleDM();
            Role role = roleDM.getRoleById(account.getRoleId());
            account.setRoleName(role.getName());
            ServiceTariffDMImpl serviceTariffDM = DMFactoryImpl.getInstance().getServiceTariffDM();
            account.setActiveServices(serviceTariffDM.getAllAccountServices(account.getId()));
        }
        return account;
    }
    //--------------

    /* Get certain number of accounts. Need for Pagination. */
    public List<Account> getAccounts(int page, int pageSize) {
        List<Account> accountList = new ArrayList<>();
        int accountsToGet = (page - 1) * pageSize;
        for (Account account : getAccountDAO().getAccounts(pageSize, accountsToGet)) {
            accountList.add(setAccountRoleName(account));
        }
        return accountList;
    }

    public List<Account> getAllAccounts() {
        return getAccountDAO().getAllAccounts();
    }

    public int getAccountCount() {
        return getAccountDAO().getAccountCount();
    }

    //Setters
    private Account setAccountRoleName(Account account) {
        RoleDMImpl roleDM = DMFactoryImpl.getInstance().getRoleDM();
        account.setRoleName(roleDM.getRoleById(account.getRoleId()).getName());
        return account;
    }

    //Adders
    /* Update account in DB */
    public void applyAccountData(Account account) {
        AccountDAOImpl accountDAO = getAccountDAO();
        if (findAccountByIdOrNull(account.getId()) != null) {
            LOG.debug("Money: " + account.getMoneyBalance());
            accountDAO.updateAccount(account);
            return;
        }
        account.setPassword(Encryption.encrypt(account.getPassword()));
        accountDAO.addAccount(account);
    }

    //Updaters
    /**.
     * Check if password is correct
     * Encrypt and set password to account
     * Update Account in DB
     * */
    public boolean changePassword(long account_id, String oldPass, String newPass, String newPassRepeat) {
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

    /* Change Account money amount and update account in DB*/
    public void topUpBalance(long account_id, int amount) {
        if (amount < 20) {
            return;
        }
        Account account = findAccountByIdOrNull(account_id);
        account.setMoneyBalance(account.getMoneyBalance()+amount);
        applyAccountData(account);
    }

    //Generators

    //Create new account
    public Account createNewAccount() {
        Account account = new Account();
        Role userRole = DMFactoryImpl.getInstance().getRoleDM().getRoleByName("user");

        account.setId((long) (getAccountDAO().getLastAccountId() + 1));
        account.setRoleId(userRole.getId());
        account.setRoleName(userRole.getName());
        account.setLogin(generateNewLogin());
        account.setPassword(generatePassword(9));
        account.setMoneyBalance(0);

        return account;
    }

    //Generate New Account login
    public int generateNewLogin() {
        int accountCount = getAccountDAO().getLastAccountId();
        int newId = maxAccountCount - (accountCount + 1);
        StringBuilder newLogin = new StringBuilder(String.valueOf(newId)).reverse();
        int nullsToAdd = String.valueOf(maxAccountCount).length() - String.valueOf(newLogin).length();
        for (int i = 0; i < nullsToAdd; i++) {
            newLogin.append(0);
        }
        return Integer.parseInt(newLogin.toString());
    }

    //Generate new password
    public String generatePassword(int length) {
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

    /**
     * Check if service and tariff that we want to add to account
     * is already exist in account data
     * If exist - update
     * if not - add as new
     * */
    public void applyServiceToAccount(long accountId, long serviceId, long tariffId) {
        LOG.debug("Applying Service [" + serviceId + "] with tariff [" + tariffId + "] To Account [" + accountId + "]");
        ServiceTariffDMImpl serviceTariffDM = DMFactoryImpl.getInstance().getServiceTariffDM();
        AccountService linkedServices = serviceTariffDM.getAccountService(accountId, serviceId);
        if (linkedServices != null) {
            LOG.debug("Service already activated, updating...");
            serviceTariffDM.updateTariffAccountService(linkedServices, tariffId);
        } else {
            LOG.debug("Service not activated yet, activating...");
            addAccountService(accountId, serviceId, tariffId);
        }
    }

    /* Add service and service tariff to account. Update account linked services data in DB */
    private void addAccountService(long accountId, long serviceId, long tariffId) {
        ServiceTariffDMImpl serviceTariffDM = DMFactoryImpl.getInstance().getServiceTariffDM();
        Date activationDate = Date.valueOf(LocalDate.now());
        AccountService accountService = new AccountService();
        accountService.setAccountId(accountId);
        accountService.setServiceId(serviceId);
        accountService.setTariffId(tariffId);
        accountService.setStatus(false);
        accountService.setPayed(false);
        accountService.setActivationTime(activationDate);
        int tariffPrice = serviceTariffDM.getTariffById(tariffId).getPrice();
        accountService.setPaymentAmount(tariffPrice);
        LOG.debug("New Tariff - " + serviceTariffDM.getTariffById(tariffId).getName() + "; Price: " + tariffPrice);
        getAccountDAO().activateServiceToAccount(accountService);
    }

    public void pauseServiceOnAccount(long accountId, long serviceId) {
        ServiceTariffDMImpl serviceTariffDM = DMFactoryImpl.getInstance().getServiceTariffDM();
        AccountService linkedServices = serviceTariffDM.getAccountService(accountId, serviceId);
        linkedServices.setStatus(false);
        System.out.println("isPayed?? >>" + linkedServices.isPayed());
        DAOFactoryImpl.getInstance().getAccountDAO().updateServiceToAccount(linkedServices);
        checkAccountStatus(accountId);
    }

    /**.
     * Check if service that we want to start
     * is already payed.
     * If true - just start it.
     * If not - get money from account for this service\tariff.
     *
     * Update account linked services data in DB.
     * */
    public void startServiceOnAccount(long accountId, long serviceId) {
        ServiceTariffDMImpl serviceTariffDM = DMFactoryImpl.getInstance().getServiceTariffDM();
        AccountService linkedServices = serviceTariffDM.getAccountService(accountId, serviceId);
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
            applyAccountData(account);
        }
        System.out.println("isPayed?? >>" + linkedServices.isPayed());
        getAccountDAO().updateServiceToAccount(linkedServices);
    }

    //Decrease money balance from account
    private void getPayForService(long accountId, int paymentAmount) {
        LOG.debug("I'll get + " + paymentAmount);
        Account account = findAccountByIdOrNull(accountId);
        account.setMoneyBalance(account.getMoneyBalance() - paymentAmount);
        applyAccountData(account);
    }

    public void disableService(long accountId, int serviceId) {
        LOG.debug("Disabling...");
        DAOFactoryImpl.getInstance().getAccountDAO().disableServiceFromAccount(accountId, serviceId);
        checkAccountStatus(accountId);
    }

    /* If Account has no active services - set account status as disabled. */
    private void checkAccountStatus(long accountId) {
        ServiceTariffDMImpl serviceTariffDM = DMFactoryImpl.getInstance().getServiceTariffDM();
        int linkedServices = serviceTariffDM.getActiveAccountServiceCount(accountId);
        if (linkedServices <= 0) {
            Account account = findAccountByIdOrNull(accountId);
            account.setAccountStatus(false);
            applyAccountData(account);
        }
    }

    public AccountDAOImpl getAccountDAO() {
        return DAOFactoryImpl.getInstance().getAccountDAO();
    }
}


























