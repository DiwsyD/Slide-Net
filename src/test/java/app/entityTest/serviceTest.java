package app.entityTest;

import app.entity.Service;
import app.entity.Tariff;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

public class serviceTest {

    @Test
    public void tariffListManipulations() {
        Service service = new Service();
        service.setId(1L);
        service.setName("Test Service");

        List<Tariff> tariffList = new ArrayList<>();
        Tariff newTariff1 = new Tariff();
        Tariff newTariff2 = new Tariff();

        newTariff1.setName("bob");
        newTariff1.setId(1L);
        newTariff2.setName("foo");
        newTariff2.setId(7L);

        tariffList.add(newTariff1);

        service.setTariffList(tariffList);

        assertEquals(1, service.getTariffList().size());

        service.addTariff(newTariff2);

        assertEquals(newTariff2, service.getTariffById(7L));

        newTariff1.setId(2L);

        service.updateTariff(newTariff1);

        assertEquals(newTariff1, service.getTariffById(2L));
    }
}
