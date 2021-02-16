package app.database.dao;

import app.database.conf.ConnectionPool;
import app.database.conf.ConstantQuery;
import app.entity.Account;
import app.entity.AccountService;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountUserDAO extends AbstractDAO{
    private static final Logger LOG = Logger.getLogger(AccountUserDAO.class);

    private static AccountUserDAO accountUserDAO = new AccountUserDAO();

    private AccountUserDAO() {
        if (connectionPool == null) {
            connectionPool = ConnectionPool.getInstance();
        }
    }

    public static AccountUserDAO getInstance() {
        return accountUserDAO;
    }

    public Account getAccountById(long id) {
        Account account = new Account();
        try(Connection con = connectionPool.getConnection()){
            PreparedStatement pst = con.prepareStatement(ConstantQuery.GET_ACCOUNT_BY_ID);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                account.setId(id);
                account.setRoleId(rs.getLong(ConstantQuery.ROLE_ID));

                account.setLogin(rs.getInt(ConstantQuery.LOGIN));
                account.setPassword(rs.getString(ConstantQuery.PASSWORD));

                account.setfName(rs.getString(ConstantQuery.FIRST_NAME));
                account.setlName(rs.getString(ConstantQuery.LAST_NAME));
                account.setsName(rs.getString(ConstantQuery.SECOND_NAME));

                account.setAddress(rs.getString(ConstantQuery.ADDRESS));
                account.setPhoneNumber(rs.getString(ConstantQuery.PHONE_NUMBER));
                account.setIpAddress(rs.getString(ConstantQuery.IP_ADDRESS));
                account.setMoneyBalance(rs.getInt(ConstantQuery.BALANCE));

                account.setAccountStatus(rs.getBoolean(ConstantQuery.ACCOUNT_STATUS));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByLogin(int login) {
        Account account = new Account();
        try(Connection con = connectionPool.getConnection()){
            PreparedStatement pst = con.prepareStatement(ConstantQuery.GET_ACCOUNT_BY_LOGIN);
            pst.setLong(1, login);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                account.setId(rs.getLong(ConstantQuery.ID));
                account.setRoleId(rs.getLong(ConstantQuery.ROLE_ID));

                account.setLogin(login);
                account.setPassword(rs.getString(ConstantQuery.PASSWORD));

                account.setfName(rs.getString(ConstantQuery.FIRST_NAME));
                account.setlName(rs.getString(ConstantQuery.LAST_NAME));
                account.setsName(rs.getString(ConstantQuery.SECOND_NAME));

                account.setAddress(rs.getString(ConstantQuery.ADDRESS));
                account.setPhoneNumber(rs.getString(ConstantQuery.PHONE_NUMBER));
                account.setIpAddress(rs.getString(ConstantQuery.IP_ADDRESS));
                account.setMoneyBalance(rs.getInt(ConstantQuery.BALANCE));

                account.setAccountStatus(rs.getBoolean(ConstantQuery.ACCOUNT_STATUS));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Account> getAccounts(int start, int end) {
        List<Account> accountList = new ArrayList<>();
        try (Connection con = connectionPool.getConnection()){
            PreparedStatement pst = con.prepareStatement(ConstantQuery.GET_LIMITED_ACCOUNTS);
            pst.setInt(1, start);
            pst.setInt(2, end);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong(ConstantQuery.ID));
                account.setRoleId(rs.getLong(ConstantQuery.ROLE_ID));

                account.setLogin(rs.getInt(ConstantQuery.LOGIN));
                account.setPassword(rs.getString(ConstantQuery.PASSWORD));

                account.setfName(rs.getString(ConstantQuery.FIRST_NAME));
                account.setlName(rs.getString(ConstantQuery.LAST_NAME));
                account.setsName(rs.getString(ConstantQuery.SECOND_NAME));

                account.setAddress(rs.getString(ConstantQuery.ADDRESS));
                account.setPhoneNumber(rs.getString(ConstantQuery.PHONE_NUMBER));
                account.setIpAddress(rs.getString(ConstantQuery.IP_ADDRESS));
                account.setMoneyBalance(rs.getInt(ConstantQuery.BALANCE));

                account.setAccountStatus(rs.getBoolean(ConstantQuery.ACCOUNT_STATUS));

                accountList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return accountList;
        }
    }

    public int getAccountCount() {
        int accountCount = 0;
        try (Connection con = connectionPool.getConnection()){
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(ConstantQuery.GET_ACCOUNT_COUNT);
            if(rs.next()) {
                accountCount = rs.getInt(ConstantQuery.ACCOUNT_TABLE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountCount;
    }

    public int getLastAccountId() {
        int maxId = -2;
        try (Connection con = connectionPool.getConnection()){
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(ConstantQuery.GET_LAST_ACCOUNT_ID);
            if(rs.next()) {
                maxId = rs.getInt(ConstantQuery.MAX_ACCOUNT_ID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId;
    }

    public void addAccount(Account account) {
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.ADD_NEW_ACCOUNT);
            pst.setLong(1, account.getRoleId());

            pst.setInt(2, account.getLogin());
            pst.setString(3, account.getPassword());

            pst.setString(4, account.getfName());
            pst.setString(5, account.getlName());
            pst.setString(6, account.getsName());

            pst.setString(7, account.getAddress());
            pst.setString(8, account.getPhoneNumber());
            pst.setString(9, account.getIpAddress());
            pst.setInt(10, account.getMoneyBalance());

            pst.setBoolean(11, account.isAccountStatus());

            pst.execute();

        } catch (SQLException e) {
            LOG.warn("Can't add new Account!");
            e.printStackTrace();
        }
    }

    public void updateAccount(Account account) {
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.UPDATE_ACCOUNT_DATA);
            pst.setLong(1, account.getRoleId());

            pst.setInt(2, account.getLogin());
            pst.setString(3, account.getPassword());

            pst.setString(4, account.getfName());
            pst.setString(5, account.getlName());
            pst.setString(6, account.getsName());

            pst.setString(7, account.getAddress());
            pst.setString(8, account.getPhoneNumber());
            pst.setString(9, account.getIpAddress());

            pst.setInt(10, account.getMoneyBalance());

            pst.setBoolean(11, account.isAccountStatus());

            pst.setLong(12, account.getId());

            pst.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Account Service Actions
    public void activateServiceToAccount(AccountService accountService) {
        LOG.debug("Activating: " + accountService.toString());
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.ADD_SERVICE_ACCOUNT);
            pst.setLong(1, accountService.getAccountId());
            pst.setLong(2, accountService.getServiceId());
            pst.setLong(3, accountService.getTariffId());
            pst.setDate(4, accountService.getActivationTime());
            pst.setBoolean(5, accountService.isStatus());
            pst.setDate(6, accountService.getNexPaymentDay());
            pst.execute();
        } catch (SQLException e) {
            LOG.error("Failed to activate!");
            e.printStackTrace();
        }
        LOG.debug("Activated!");
    }

    public void updateServiceToAccount(AccountService accountService) {
        LOG.debug("Updating: " + accountService.toString());
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.UPDATE_SERVICE_ACCOUNT);
            pst.setLong(1, accountService.getTariffId());
            pst.setDate(2, accountService.getActivationTime());
            pst.setBoolean(3, accountService.isStatus());
            pst.setDate(4, accountService.getNexPaymentDay());

            pst.setLong(5, accountService.getAccountId());
            pst.setLong(6, accountService.getServiceId());
            pst.execute();
        } catch (SQLException e) {
            LOG.error("Failed to updatee!");
            e.printStackTrace();
        }
        LOG.debug("Updated!");
    }

    public void disableServiceFromAccount(long accountId, int serviceId) {
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.REMOVE_SERVICE_ACCOUNT);
            pst.setLong(1, accountId);
            pst.setInt(2, serviceId);
            pst.execute();
        } catch (SQLException e) {
            LOG.error("Failed to disable!");
            e.printStackTrace();
        }
        LOG.debug("Disabled!");
    }
}



































