package app.factory;

import app.entityDataManager.impl.AccountDMImpl;
import app.entityDataManager.impl.RoleDMImpl;
import app.entityDataManager.impl.ServiceTariffDMImpl;

public interface DMFactory {

    AccountDMImpl getAccountDM();

    RoleDMImpl getRoleDM();

    ServiceTariffDMImpl getServiceTariffDM();
}
