package app.dao.Impl;

import app.constants.ConstantQuery;
import app.connectionpool.ConnectionPool;
import app.dao.RoleDAO;
import app.entity.Role;
import app.entityDataManager.impl.ServiceTariffDMImpl;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAOImpl implements RoleDAO {
    private static final Logger LOG = Logger.getLogger(ServiceTariffDMImpl.class);

    ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final RoleDAOImpl roleDAOImpl = new RoleDAOImpl();

    private RoleDAOImpl() {
        if (connectionPool == null) {
            connectionPool = ConnectionPool.getInstance();
        }
    }

    public static RoleDAOImpl getInstance() {
        return roleDAOImpl;
    }

    /**.
     * Just for Tests.
     * */
    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Role getRoleById(long id) {
        Role role = null;
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.GET_ROLE_BY_ID);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                role = new Role();
                role.setId(id);
                role.setName(rs.getString(ConstantQuery.ROLE_NAME));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    public Role getRoleByName(String name) {
        Role role = null;
        try (Connection con = connectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(ConstantQuery.GET_ROLE_BY_NAME);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                role = new Role();
                role.setId(rs.getLong(ConstantQuery.ID));
                role.setName(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

}
