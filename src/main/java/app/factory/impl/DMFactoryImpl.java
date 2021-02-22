package app.factory.impl;

import app.entityDataManager.impl.AccountDMImpl;
import app.entityDataManager.impl.RoleDMImpl;
import app.entityDataManager.impl.ServiceTariffDMImpl;

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
