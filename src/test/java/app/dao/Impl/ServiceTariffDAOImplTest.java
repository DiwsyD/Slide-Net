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
    private static Tariff tar;

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
        assertTrue(services.size() > 0);
    }

    public void testGetAllServicesWithoutTariffs() {
        List<Service> services = serviceTariffDAO.getAllServicesWithoutTariffs();
        assertEquals(5, services.size());
    }

    public void testGetAllAccountServicesByAccountId() {
        List<AccountService> services = serviceTariffDAO.getAllAccountServicesByAccountId(1);
        assertNotNull(services);
    }

    public void testGetAccountServiceByAccountId() {
        AccountService services = serviceTariffDAO.getAccountServiceByAccountId(1, 1);
        assertNull(services);
    }

    public void testGetActiveAccountServiceCount() {
        int servicesCount = serviceTariffDAO.getActiveAccountServiceCount(1);
        assertNotNull(servicesCount);
    }

    public void testGetTariffsByServiceId() {
        List<Tariff> tariff = serviceTariffDAO.getTariffsByServiceId(1);
        assertEquals(3, tariff.size());
    }

    public void testGetPartTariffsByServiceId() {
        List<Tariff> tariff = serviceTariffDAO.getPartTariffsByServiceId(1, 2, 0, "price", "desc");
        assertEquals(2, tariff.size());
    }

    public void testGetServiceTariffCount() {
        int tariffCount = serviceTariffDAO.getServiceTariffCount(4);
        assertEquals(5, tariffCount);
    }

    public void testCheckTariffIsExist() {
        long id = 10L;
        Tariff tariff = new Tariff();
        tariff.setName("Basic");
        long tariffId = serviceTariffDAO.checkTariffIsExist(tariff);
        assertEquals(id, tariffId);
    }

    public void testGetTariffById() {
        Tariff tariff = serviceTariffDAO.getTariffById(3L);
        assertEquals("Lux 1G", tariff.getName());
    }

    public void testAddNewTariff() {
        serviceTariffDAO.addNewTariff(tar);
        verify(tar, times(1)).getName();
    }

    public void testEditTariff() {
        Tariff tariff = mock(Tariff.class);
        tariff.setId(1L);
        tariff.setServiceId(1L);
        tariff.setPrice(225);
        tariff.setName("Turbo 300");
        tariff.setDescription("Up to 300 Mbps");
        serviceTariffDAO.editTariff(tariff);
        verify(tariff).getName();
    }

    public void testGetServiceCount() {
        int serviceCount = serviceTariffDAO.getServiceCount();
        assertEquals(5, serviceCount);
    }

    public void testGetTariffCount() {
        int tariffCount = serviceTariffDAO.getTariffCount();
        assertEquals(17, tariffCount);
    }
}