package app.dao.Impl;

import app.connectionpool.ConnectionPool;
import app.dao.Impl.AccountDAOImpl;
import app.entity.Account;
import app.entity.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AccountDAOTest {

    @Mock
    private static ConnectionPool CP;
    @Mock
    private static Account acc;
    @Mock
    private static AccountService accountService;

    private static final AccountDAOImpl accountDAO = AccountDAOImpl.getInstance();

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/slidenetdb", "testuser", "testpass");
        doReturn(connection).when(CP).getConnection();
        accountDAO.setConnectionPool(CP);
    }

    @Test
    public void getAccountById() {
        Account account = accountDAO.getAccountById(1);
        assertNotNull(account);
    }

    @Test
    public void testGetAccountByLogin() {
        Account account = accountDAO.getAccountByLogin(854652030);
        assertNotNull(account);
    }

    @Test
    public void testGetAccounts() {
        List<Account> accounts = accountDAO.getAccounts(5, 0);
        assertEquals(4, accounts.size());
    }

    @Test
    public void testGetAccountCount() {
        int accountCount = accountDAO.getAccountCount();
        assertEquals(4, accountCount);
    }

    @Test
    public void testGetLastAccountId() {
        int accountCount = accountDAO.getLastAccountId();
        assertNotNull(accountCount);
    }

    @Test
    public void testAddAccount() {
        accountDAO.addAccount(acc);
        verify(acc, times(1)).getPassword();
    }

    @Test
    public void testUpdateAccount() {
        accountDAO.addAccount(acc);
        verify(acc, times(1)).getPassword();
    }

    @Test
    public void testActivateServiceToAccount() {
        accountDAO.activateServiceToAccount(accountService);
        verify(accountService, times(1)).getAccountId();
    }

    @Test
    public void testUpdateServiceToAccount() {
        accountDAO.updateServiceToAccount(accountService);
        verify(accountService, times(1)).getAccountId();
    }

    @Test
    public void testDisableServiceFromAccount() {
        accountDAO.disableServiceFromAccount(1, 1);
    }



}
