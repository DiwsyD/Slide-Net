package app.service;

import app.dao.Impl.RoleDAOImpl;
import app.entity.Role;
import org.apache.log4j.Logger;

public class RoleDataManager {

    private RoleDataManager() {}
    private static final Logger LOG = Logger.getLogger(RoleDataManager.class);

    public static Role getRoleById(long id) {
        return RoleDAOImpl.getInstance().getRoleById(id);
    }

    public static Role getRoleByName(String name) {
        return RoleDAOImpl.getInstance().getRoleByName(name);
    }

}
