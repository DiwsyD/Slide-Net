package app.factory.Impl;

import app.dao.Impl.AccountDAOImpl;
import app.dao.Impl.RoleDAOImpl;
import app.dao.Impl.ServiceTariffDAOImpl;
import junit.framework.TestCase;

public class DAOFactoryImplTest extends TestCase {

    DAOFactoryImpl factory = DAOFactoryImpl.getInstance();

    public void testGetAccountDAO() {
        AccountDAOImpl accountDAOExp = AccountDAOImpl.getInstance();
        AccountDAOImpl accountDAO = factory.getAccountDAO();
        assertEquals(accountDAOExp, accountDAO);
    }

    public void testGetRoleDAO() {
        RoleDAOImpl roleDAOExp = RoleDAOImpl.getInstance();
        RoleDAOImpl roleDAO = factory.getRoleDAO();
        assertEquals(roleDAOExp, roleDAO);
    }

    public void testGetServiceTariffDAO() {
        ServiceTariffDAOImpl serviceTariffDAOExp = ServiceTariffDAOImpl.getInstance();
        ServiceTariffDAOImpl serviceTariffDAO = factory.getServiceTariffDAO();
        assertEquals(serviceTariffDAOExp, serviceTariffDAO);
    }
}