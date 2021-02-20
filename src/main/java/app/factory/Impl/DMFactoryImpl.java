package app.factory.Impl;

import app.entityDataManager.Impl.AccountDMImpl;
import app.entityDataManager.Impl.RoleDMImpl;
import app.entityDataManager.Impl.ServiceTariffDMImpl;

public class DMFactoryImpl {

    private static DMFactoryImpl DMFactoryImpl = new DMFactoryImpl();

    private DMFactoryImpl(){}

    public static DMFactoryImpl getInstance() {
        return DMFactoryImpl;
    }

    public AccountDMImpl getAccountDM() {
        return new AccountDMImpl();
    }

    public RoleDMImpl getRoleDM() {
        return new RoleDMImpl();
    }

    public ServiceTariffDMImpl getServiceTariffDM() {
        return new ServiceTariffDMImpl();
    }

}
