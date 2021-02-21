package app.dao.Impl;

import app.connectionpool.ConnectionPool;
import app.entity.AccountService;
import app.entity.Service;
import app.entity.Tariff;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.mockito.Mockito.*;

public class ServiceTariffDAOImplTest extends TestCase {
    @Mock
    Connection connection;
    @Mock
    private static ConnectionPool CP;
    @Mock
    private static Service serv;
    @Mock
    private static Tariff tar;
    @Mock
    private static AccountService accountService;

    private static final ServiceTariffDAOImpl serviceTariffDAO = ServiceTariffDAOImpl.getInstance();

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/slidenetdb", "testuser", "testpass");
        doReturn(connection).when(CP).getConnection();
        serviceTariffDAO.setConnectionPool(CP);
    }

    public void testGetServiceById() {
        Service service = serviceTariffDAO.getServiceById(1);
        assertNotNull(service);
    }

    public void testGetAllServices() throws SQLException {
        List<Service> services = serviceTariffDAO.getAllServices();
        assertEquals(1, services.size());
    }

    public void testGetAllServicesWithoutTariffs() {
    }

    public void testGetAccountServicesByAccountId() {
    }

    public void testGetAccountServiceByAccountId() {
    }

    public void testGetActiveAccountService() {
    }

    public void testGetTariffsByServiceId() {
    }

    public void testGetPartTariffsByServiceId() {
    }

    public void testGetServiceTariffCount() {
    }

    public void testCheckTariffIsExist() {
    }

    public void testGetTariffById() {
    }

    public void testAddNewTariff() {
    }

    public void testRemoveTariff() {
    }

    public void testEditTariff() {
    }

    public void testGetServiceCount() {
    }

    public void testGetTariffCount() {
    }
}