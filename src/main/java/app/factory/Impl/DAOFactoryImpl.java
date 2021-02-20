package app.factory.Impl;

import app.dao.Impl.AccountDAOImpl;
import app.dao.Impl.RoleDAOImpl;
import app.dao.Impl.ServiceTariffDAOImpl;
import app.factory.DAOFactory;

public class DAOFactoryImpl implements DAOFactory {

    private final AccountDAOImpl accountDAOImpl = AccountDAOImpl.getInstance();
    private final RoleDAOImpl roleDAOImpl = RoleDAOImpl.getInstance();
    private final ServiceTariffDAOImpl serviceTariffDAOImpl = ServiceTariffDAOImpl.getInstance();

    private static final DAOFactoryImpl DAOFactoryImpl = new DAOFactoryImpl();

    private DAOFactoryImpl(){}

    public static DAOFactoryImpl getInstance() {
        return DAOFactoryImpl;
    }

    public AccountDAOImpl getAccountDAO() {return accountDAOImpl;}

    public  RoleDAOImpl getRoleDAO() {
        return roleDAOImpl;
    }

    public  ServiceTariffDAOImpl getServiceTariffDAO() {
        return serviceTariffDAOImpl;
    }
}
