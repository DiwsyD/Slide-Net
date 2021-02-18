package app.service;

import app.entity.Service;
import app.entity.Tariff;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class DowsnloadFile {
    private static final Logger LOG = Logger.getLogger(DowsnloadFile.class);

    private static final String FILE_NAME = "SlideNet_TariffPlans";
    private static final String FILE_RELATIVE_PATH = "media/textFiles/";

    public static void downloadServices(HttpServletResponse resp, String absPath) throws IOException {
        PrintWriter out = resp.getWriter();

        resp.setContentType("text/html");
        resp.setContentType("APPLICATION/OCTET-STREAM");
        resp.setHeader("Content-Disposition","attachment; filename=\"" + FILE_NAME + ".txt" + "\"");

        out.write(generateServiceInformation());
        out.close();
    }

    private static String generateServiceInformation() {
        List<Service> serviceList = ServiceTariffDataManager.getAllServices();
        StringBuilder sb = new StringBuilder();
        String newLine = "\n";
        String tab = "    ";
        String lineSep = "-----------------------------";
        sb.append("Slide-Net Service Tariffs!").append(newLine)
                .append(lineSep).append(newLine).append(newLine);
        for (Service service : serviceList) {
            sb.append(">").append(service.getName())
                    .append(newLine).append(tab)
                    .append("Tariffs:");
            for (Tariff tariff : service.getTariffList()) {
                sb.append(newLine).append(tab).append(tab)
                .append("[Name]: ").append(tariff.getName())
                        .append(newLine).append(tab).append(tab)
                .append("[Description]: ").append(tariff.getDescription())
                        .append(newLine).append(tab).append(tab)
                .append("[Price]: ").append(tariff.getPrice())
                        .append(newLine).append(tab).append(tab)
                        .append(lineSep);
            }
            sb.append(newLine).append(newLine);
        }
        return sb.toString();
    }
}