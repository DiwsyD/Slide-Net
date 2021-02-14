package app.model;

import app.database.dao.RoleDAO;
import app.entity.EntityManager;
import app.entity.Role;
import org.apache.log4j.Logger;

public class RoleDataManager {

    private RoleDataManager() {}
    private static final Logger LOG = Logger.getLogger(RoleDataManager.class);

    public static Role getRoleById(long id) {
        Role role = EntityManager.getInstance().getRoleById(id);
        if (role != null) {
            return role;
        }

        role = RoleDAO.getInstance().getRoleById(id);
        if (role != null) {
            addRoleToEntityManager(role);
        }
        return role;
    }

    public static Role getRoleByName(String name) {
        Role role = EntityManager.getInstance().getRoleByName(name);
        if (role != null) {
            return role;
        }

        role = RoleDAO.getInstance().getRoleByName(name);
        if (role != null) {
            addRoleToEntityManager(role);
        }
        return role;
    }

    private static void addRoleToEntityManager(Role role) {
        EntityManager.getInstance().addRole(role);
    }
}
