package app.factory.impl;

import app.entityDataManager.impl.AccountDMImpl;
import app.entityDataManager.impl.RoleDMImpl;
import app.entityDataManager.impl.ServiceTariffDMImpl;
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