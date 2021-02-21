package app.factory.Impl;

import app.dao.Impl.AccountDAOImpl;
import app.entityDataManager.Impl.AccountDMImpl;
import app.entityDataManager.Impl.RoleDMImpl;
import app.entityDataManager.Impl.ServiceTariffDMImpl;
import junit.framework.TestCase;

public class DMFactoryImplTest extends TestCase {

    DMFactoryImpl factory = DMFactoryImpl.getInstance();

    public void testGetAccountDM() {
        AccountDMImpl accountDMxp = new AccountDMImpl();
        AccountDMImpl accountDM = factory.getAccountDM();
        assertEquals(accountDMxp, accountDM);
    }

    public void testGetRoleDM() {
        RoleDMImpl roleDMExp = new RoleDMImpl();
        RoleDMImpl roleDM = factory.getRoleDM();
        assertEquals(roleDMExp, roleDM);
    }

    public void testGetServiceTariffDM() {
        ServiceTariffDMImpl serviceTariffDMExp = new ServiceTariffDMImpl();
        ServiceTariffDMImpl serviceTariffDM = factory.getServiceTariffDM();
        assertEquals(serviceTariffDMExp, serviceTariffDM);
    }
}