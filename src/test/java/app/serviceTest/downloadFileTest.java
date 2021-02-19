package app.serviceTest;

import app.service.DownloadFile;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class downloadFileTest {

    @Test
    public void downloadFile() {
        try {
            DownloadFile.downloadServices(Mockito.mock(HttpServletResponse.class));
        } catch (IOException e) {
            assert(false);
        }
        assert(true);
    }
}
