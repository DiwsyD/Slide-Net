package app.model;

import app.entity.Service;
import app.entity.Tariff;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class DowsnloadFile {
    private static final Logger LOG = Logger.getLogger(DowsnloadFile.class);

    private static final String FILE_NAME = "Slide_Net_TariffPlans";
    private static final String FILE_RELATIVE_PATH = "media/textFiles/";

    public static void downloadServices(HttpServletResponse resp, String absPath) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String filename = FILE_NAME + ".txt";
        String filepath = absPath + FILE_RELATIVE_PATH;
        resp.setContentType("APPLICATION/OCTET-STREAM");
        resp.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");
        new File(filepath + "O" + filename).createNewFile();
        FileInputStream fileInputStream = new FileInputStream(filepath + filename);
        out.write(generateServiceInformation());
        fileInputStream.close();
        out.close();
    }

    private static String generateServiceInformation() {
        List<Service> serviceList = ServiceTariffDataManager.getAllServices();
        StringBuilder sb = new StringBuilder();
        String newLine = "\n";
        String tab = "    ";
        String lineSep = "-----------------------------";
        for (Service service : serviceList) {
            sb.append(">").append(service.getName())
                    .append(newLine).append(tab)
                    .append("Tariffs:");
            for (Tariff tariff : service.getTariffList()) {
                sb.append(newLine).append(tab).append(tab)
                .append("[Name]: ").append(tariff.getName())
                .append("[Description]: ").append(tariff.getDescription())
                .append("[Price]: ").append(tariff.getPrice());
            }
            sb.append(newLine).append(lineSep).append(newLine);
        }
        return sb.toString();
    }
}
