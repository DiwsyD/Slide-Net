package app.dao.Impl;

import app.connectionpool.ConnectionPool;
import app.entity.Role;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.mockito.Mockito.doReturn;

public class RoleDAOImplTest extends TestCase {

    @Mock
    Connection connection;
    @Mock
    private static ConnectionPool CP;
    @Mock
    private static Role role;

    private static final RoleDAOImpl roleDAO = RoleDAOImpl.getInstance();

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/slidenetdb", "testuser", "testpass");
        doReturn(connection).when(CP).getConnection();
        roleDAO.setConnectionPool(CP);
    }

    @Test
    public void testGetRoleById() {
        Role roleExpected = new Role();
        roleExpected.setId(1L);
        roleExpected.setName("admin");
        Role role = roleDAO.getRoleById(1);
        assertEquals(roleExpected, role);
    }

    @Test
    public void testGetRoleByName() {
        Role roleExpected = new Role();
        roleExpected.setId(2L);
        roleExpected.setName("user");
        Role role = roleDAO.getRoleByName("user");
        assertEquals(roleExpected, role);

    }
}