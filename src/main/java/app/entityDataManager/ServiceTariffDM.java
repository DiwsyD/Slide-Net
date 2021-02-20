package app.entityDataManager;

import app.dao.Impl.ServiceTariffDAOImpl;
import app.entity.AccountService;
import app.entity.Service;
import app.entity.Tariff;

import java.util.List;

public interface ServiceTariffDM {

    Service getServiceById(long id);

    List<Service> getAllServices();

    List<Service> getAllServicesWithoutTariffs();

    List<Tariff> getCertainServiceTariffs(long service_id, int page, int pageSize, String orderBy, String desc);

    AccountService getAccountService(long accountId, long serviceId);

    List<AccountService> getAllAccountServices(long accountId);

    int getServiceTariffCount(long serviceId);

    void applyTariff(int serviceId, String tariffName, String tariffDescription, int tariffPrice);

    void removeTariff(String tariffName);

    Tariff getTariffById(long tariffId);

    int getServiceCount();

    int getTariffCount();

    void updateTariffAccountService(AccountService linkedService, long tariffId);

    void updateAccountService(AccountService accountService);

    int getActiveAccountServiceCount(long accountId);






}
