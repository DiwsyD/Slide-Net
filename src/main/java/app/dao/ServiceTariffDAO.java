package app.dao;

import app.entity.AccountService;
import app.entity.Service;
import app.entity.Tariff;

import java.util.List;

public interface ServiceTariffDAO {

    Service getServiceById(long id);

    List<Service> getAllServices();

    List<Service> getAllServicesWithoutTariffs();

    List<AccountService> getAccountServicesByAccountId(long accountId);

    AccountService getAccountServiceByAccountId(long accountId, long serviceId);

    int getActiveAccountService(long accountId);

    List<Tariff> getTariffsByServiceId(long id);

    List<Tariff> getPartTariffsByServiceId(long service_id, int start, int end, String orderBy, String desc);

    int getServiceTariffCount(long id);

    long checkTariffIsExist(Tariff tariff);

    Tariff getTariffById(long tariffId);

    void addNewTariff(Tariff tariff);

    void removeTariff(String tariff);

    void editTariff(Tariff tariff);

    int getServiceCount();

    int getTariffCount();
}
