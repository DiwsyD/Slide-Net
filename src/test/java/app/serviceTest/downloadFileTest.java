package app.serviceTest;

import app.entity.Service;
import app.entity.Tariff;
import app.factory.Impl.DMFactoryImpl;
import app.service.DownloadFile;
import app.entityDataManager.Impl.ServiceTariffDMImpl;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class downloadFileTest {

    @Test
    public void generateInfoWithNullListTest() {
        String expected = "Slide-Net Service Tariffs!";
        String result = DownloadFile.generateServiceInformation(null);

        assertEquals(expected, result);
    }

    @Test
    public void generateInfoWithEmptyListTest() {
        String expected = "Slide-Net Service Tariffs!";
        List<Service> mockedSlit = new ArrayList<>();
        String result = DownloadFile.generateServiceInformation(mockedSlit);

        assertEquals(expected, result);
    }

    @Test
    public void generateInfoWithOneNullElementTest() {
        String expected = "Slide-Net Service Tariffs!" +
                "\n-----------------------------\n" +
                "\n>Empty Service;\n";
        List<Service> mockedSlit = new ArrayList<>();
        mockedSlit.add(null);
        String result = DownloadFile.generateServiceInformation(mockedSlit);

        assertEquals(expected, result);
    }

    @Test
    public void generateInfoWithOneEmptyElementTest() {
        String expected = "Slide-Net Service Tariffs!" +
                "\n-----------------------------\n" +
                "\n>null\n" +
                "    Tariffs: There is no tariffs yet :(";
        Service mockedService = mock(Service.class);
        Tariff mockedTariff = mock(Tariff.class);
        List<Tariff> mockedTariffList = new ArrayList<>();

        mockedTariffList.add(mockedTariff);
        mockedTariffList.add(mockedTariff);
        mockedTariffList.add(mockedTariff);

        mockedService.setTariffList(mockedTariffList);

        List<Service> mockedSlit = new ArrayList<>();
        mockedSlit.add(mockedService);

        String result = DownloadFile.generateServiceInformation(mockedSlit);

        assertEquals(expected, result);
    }

    @Test
    public void downloadServicesMethodTest() throws IOException {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        PrintWriter prwr = mock(PrintWriter.class);
        ServiceTariffDMImpl stdm = mock(ServiceTariffDMImpl.class);

        when(resp.getWriter()).thenReturn(prwr);

        when(DMFactoryImpl.getInstance().getServiceTariffDM().getAllServices()).thenReturn(null);

        DownloadFile.downloadServices(resp);

    }
}

































