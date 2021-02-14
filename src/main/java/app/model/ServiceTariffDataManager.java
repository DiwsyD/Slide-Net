package app.model;

import app.database.dao.ServiceTariffDAO;
import app.entity.AccountService;
import app.entity.EntityManager;
import app.entity.Service;
import app.entity.Tariff;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ServiceTariffDataManager {
    private static final Logger LOG = Logger.getLogger(ServiceTariffDataManager.class);

    private ServiceTariffDataManager() {}

    public static Service getServiceById(long id) {
        return ServiceTariffDAO.getInstance().getServiceById(id);
    }

    public static List<Service> getAllServices() {
        return ServiceTariffDAO.getInstance().getAllServices();
    }
    public static List<Service> getAllServicesWithoutTariffs() {
        return ServiceTariffDAO.getInstance().getAllServicesWithoutTariffs();
    }

    public static List<Tariff> getAllServiceTariffs(long serviceId) {
        return ServiceTariffDAO.getInstance().getTariffsByServiceId(serviceId);
    }

    public static List<Tariff> getCertainServiceTariffs(long service_id, int page, int pageSize, String orderBy, String desc) {
        int tariffsToGet = (page - 1) * pageSize;
        return ServiceTariffDAO.getInstance().getPartTariffsByServiceId(service_id, pageSize, tariffsToGet, orderBy, desc);
    }

    public static List<AccountService> getAccountServices(long accountId) {
        return ServiceTariffDAO.getInstance().getAccountServicesByAccountId(accountId);
    }

    public static int getServiceTariffCount(long sId) {
        return ServiceTariffDAO.getInstance().getServiceTariffCount(sId);
    }

    //Tariff actions
    public static void applyTariff(int serviceId, String tariffName, String tariffDescription, int tariffPrice) {
        Tariff tariff = new Tariff();

        tariff.setServiceId(serviceId);
        tariff.setName(tariffName);
        tariff.setDescription(tariffDescription);
        tariff.setPrice(tariffPrice);

        LOG.debug(tariff.toString());

        long id = ServiceTariffDAO.getInstance().checkTariffIsExist(tariff);
        if (id > 0) {
            tariff.setId(id);
            ServiceTariffDAO.getInstance().editTariff(tariff);
        } else {
            LOG.debug("ADD");
            ServiceTariffDAO.getInstance().addNewTariff(tariff);
        }

    }

    public static void removeTariff(String tariffName) {
        ServiceTariffDAO.getInstance().removeTariff(tariffName);
    }


}
