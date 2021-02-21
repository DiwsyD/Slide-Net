package app.factory.Impl;

import app.dao.Impl.AccountDAOImpl;
import app.entityDataManager.Impl.AccountDMImpl;
import app.entityDataManager.Impl.RoleDMImpl;
import app.entityDataManager.Impl.ServiceTariffDMImpl;
import junit.framework.TestCase;

public class DMFactoryImplTest extends TestCase {

    DMFactoryImpl factory = DMFactoryImpl.getInstance();

    public void testGetAccountDM() {
        AccountDMImpl accountDM = factory.getAccountDM();
        assertNotNull(accountDM);
    }

    public void testGetRoleDM() {
        RoleDMImpl roleDM = factory.getRoleDM();
        assertNotNull(roleDM);
    }

    public void testGetServiceTariffDM() {
        ServiceTariffDMImpl serviceTariffDM = factory.getServiceTariffDM();
        assertNotNull(serviceTariffDM);
    }
}