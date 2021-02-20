package app.entityDataManager;

import app.entityDataManager.Impl.AccountDMImpl;
import app.entityDataManager.Impl.RoleDMImpl;
import app.entityDataManager.Impl.ServiceTariffDMImpl;

public interface DMFactory {

    AccountDMImpl getAccountDM();

    RoleDMImpl getRoleDM();

    ServiceTariffDMImpl getServiceTariffDM();
}
