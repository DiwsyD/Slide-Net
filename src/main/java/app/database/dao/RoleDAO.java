package app.database.dao;

import app.database.conf.ConnectionPool;
import app.database.conf.ConstantQuery;
import app.entity.Role;
import app.model.ServiceTariffDataManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO extends AbstractDAO{
    private static final Logger LOG = Logger.getLogger(ServiceTariffDataManager.class);

    private static RoleDAO roleDAO = new RoleDAO();

    private RoleDAO() {
        if (connectionPool == null) {
            connectionPool = ConnectionPool.getInstance();
        }
    }

    public static RoleDAO getInstance() {
        return roleDAO;
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
