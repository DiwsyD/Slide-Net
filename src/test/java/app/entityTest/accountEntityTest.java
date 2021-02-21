package app.entityTest;

import app.entity.Account;
import app.entity.AccountService;
import app.entity.Service;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;


public class accountEntityTest {

    @Test
    public void getAccountDataTest() {
        Account account = new Account();
        Account account1 = mock(Account.class);
        AccountService accountService = mock(AccountService.class);
        Service service = mock(Service.class);
        Service service1 = new Service();

        account.setId(1L);
        account.setRoleId(1L);
        account.setRoleName("admin");
        account.setLogin(123456789L);
        account.setPassword("somePass");
        account.setfName("test");
        account.setlName("test");
        account.setsName("test");
        account.setAddress("Some Test Address");
        account.setPhoneNumber("123456789");
        account.setIpAddress("132.123.123.123");
        account.setMoneyBalance(120);
        account.setAccountStatus(true);
        account.setActiveServices(Collections.singletonList(accountService));

        assertNotEquals(null, account.getId());
        assertNotEquals(null, account.getRoleId());
        assertNotEquals(null, account.getRoleName());
        assertNotEquals(null, account.getLogin());
        assertNotEquals(null, account.getPassword());
        assertNotEquals(null, account.getfName());
        assertNotEquals(null, account.getsName());
        assertNotEquals(null, account.getlName());
        assertNotEquals(null, account.getAddress());
        assertNotEquals(null, account.getPhoneNumber());
        assertNotEquals(null, account.getIpAddress());
        assertNotEquals(null, account.getMoneyBalance());
        assertNotEquals(null, account.isAccountStatus());
        assertNotEquals(null, account.getActiveServices());
        assertNotNull(account.isServiceLinked(service));
        assertNull(account.isServiceLinked(service1));

        assertFalse(account.equals(account1));
        assertNotNull(account.toString());

    }

}
