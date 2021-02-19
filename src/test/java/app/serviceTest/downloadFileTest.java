package app.serviceTest;

import app.dao.Impl.ServiceTariffDAOImpl;
import app.service.DownloadFile;
import app.service.ServiceTariffDataManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class downloadFileTest {

    @Mock
    static Connection mockConn;

    @Mock
    static Statement mockStatement;


    @Before
    public void mockServiceDataFromDB() throws SQLException {
        ServiceTariffDataManager mockServiceTariffDataManager = mock(ServiceTariffDataManager.class);
        when(ServiceTariffDAOImpl.getInstance().getAllServices()).thenReturn(null);
        when(mockConn.createStatement(anyInt(), anyInt())).thenReturn(mockStatement);
    }

    @Test
    public void downloadFile() {
        try {
            DownloadFile.downloadServices(mock(HttpServletResponse.class));
        } catch (IOException e) {
            assert(false);
        }
        assert(true);
    }
}
