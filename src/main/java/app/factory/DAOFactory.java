package app.factory;


import app.dao.Impl.AccountDAOImpl;
import app.dao.Impl.RoleDAOImpl;
import app.dao.Impl.ServiceTariffDAOImpl;

public interface DAOFactory {

    AccountDAOImpl getAccountDAO();

    RoleDAOImpl getRoleDAO();

    ServiceTariffDAOImpl getServiceTariffDAO();

}
