package app.entityDataManager.impl;

import app.dao.Impl.RoleDAOImpl;
import app.entity.Role;
import app.entityDataManager.RoleDM;
import app.factory.impl.DAOFactoryImpl;
import org.apache.log4j.Logger;

public class RoleDMImpl implements RoleDM {
    private static final Logger LOG = Logger.getLogger(RoleDMImpl.class);

    public Role getRoleById(long id) {
        RoleDAOImpl roleDAO = DAOFactoryImpl.getInstance().getRoleDAO();
        return roleDAO.getRoleById(id);
    }

    public Role getRoleByName(String name) {
        RoleDAOImpl roleDAO = DAOFactoryImpl.getInstance().getRoleDAO();
        return roleDAO.getRoleByName(name);
    }

}
