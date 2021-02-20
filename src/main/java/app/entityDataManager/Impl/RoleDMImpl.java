package app.entityDataManager.Impl;

import app.dao.Impl.RoleDAOImpl;
import app.entity.Role;
import app.entityDataManager.RoleDM;
import app.factory.Impl.DAOFactoryImpl;
import org.apache.log4j.Logger;

public class RoleDMImpl implements RoleDM {

    private static final Logger LOG = Logger.getLogger(RoleDMImpl.class);

    public Role getRoleById(long id) {
        return DAOFactoryImpl.getInstance().getRoleDAO().getRoleById(id);
    }

    public Role getRoleByName(String name) {
        return DAOFactoryImpl.getInstance().getRoleDAO().getRoleByName(name);
    }

}
