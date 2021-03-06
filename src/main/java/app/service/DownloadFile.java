package app.service;

import app.entity.Service;
import app.entity.Tariff;
import app.entityDataManager.impl.ServiceTariffDMImpl;
import app.factory.impl.DMFactoryImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class DownloadFile {
    private static final Logger LOG = Logger.getLogger(DownloadFile.class);

    private static final String FILE_NAME = "SlideNet_TariffPlans";

    public static void downloadServices(HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();

        resp.setContentType("text/html");
        resp.setContentType("APPLICATION/OCTET-STREAM");
        resp.setHeader("Content-Disposition","attachment; filename=\"" + FILE_NAME + ".txt" + "\"");

        ServiceTariffDMImpl serviceTariffDM = DMFactoryImpl.getInstance().getServiceTariffDM();
        List<Service> serviceList = serviceTariffDM.getAllServices();

        out.write(generateServiceInformation(serviceList));
        out.close();
    }

    /**.
     * Get List of services, pack it to String value and return.
     * */
    public static String generateServiceInformation(List<Service> serviceList) {
        StringBuilder sb = new StringBuilder("Slide-Net Service Tariffs!");
        if (serviceList == null || serviceList.size() < 1) {
            return sb.toString();
        }
        String newLine = "\n";
        String tab = "    ";
        String lineSep = "-----------------------------";
        sb.append(newLine)
                .append(lineSep).append(newLine).append(newLine);
        for (Service service : serviceList) {
            if (service == null) {
                sb.append(">Empty Service;").append(newLine);
                continue;
            }
            sb.append(">").append(service.getName())
                    .append(newLine).append(tab)
                    .append("Tariffs: ");
            List<Tariff> tariffList = service.getTariffList();
            if (tariffList.size() < 1) {
                sb.append("There is no tariffs yet :(");
                continue;
            }
            for (Tariff tariff : tariffList) {
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
