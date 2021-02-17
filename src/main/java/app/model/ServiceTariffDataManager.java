package app.model;

import app.dao.DAOImpl.AccountDAOImpl;
import app.dao.DAOImpl.ServiceTariffDAOImpl;
import app.entity.AccountService;
import app.entity.Service;
import app.entity.Tariff;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ServiceTariffDataManager {
    private static final Logger LOG = Logger.getLogger(ServiceTariffDataManager.class);

    public static final int DAY_IN_MONTH = 30;

    private ServiceTariffDataManager() {}

    public static Service getServiceById(long id) {
        return ServiceTariffDAOImpl.getInstance().getServiceById(id);
    }

    public static List<Service> getAllServices() {
        return ServiceTariffDAOImpl.getInstance().getAllServices();
    }
    public static List<Service> getAllServicesWithoutTariffs() {
        return ServiceTariffDAOImpl.getInstance().getAllServicesWithoutTariffs();
    }

    public static List<Tariff> getAllServiceTariffs(long serviceId) {
        return ServiceTariffDAOImpl.getInstance().getTariffsByServiceId(serviceId);
    }

    public static List<Tariff> getCertainServiceTariffs(long service_id, int page, int pageSize, String orderBy, String desc) {
        int tariffsToGet = (page - 1) * pageSize;
        return ServiceTariffDAOImpl.getInstance().getPartTariffsByServiceId(service_id, pageSize, tariffsToGet, orderBy, desc);
    }

    public static AccountService getAccountService(long accountId, long serviceId) {
        return ServiceTariffDAOImpl.getInstance().getAccountServiceByAccountId(accountId, serviceId);
    }

    public static List<AccountService> getAllAccountServices(long accountId) {
        return ServiceTariffDAOImpl.getInstance().getAccountServicesByAccountId(accountId);
    }

    public static int getServiceTariffCount(long serviceId) {
        return ServiceTariffDAOImpl.getInstance().getServiceTariffCount(serviceId);
    }

    //Tariff actions
    public static void applyTariff(int serviceId, String tariffName, String tariffDescription, int tariffPrice) {
        Tariff tariff = new Tariff();

        tariff.setServiceId(serviceId);
        tariff.setName(tariffName);
        tariff.setDescription(tariffDescription);
        tariff.setPrice(tariffPrice);

        LOG.debug(tariff.toString());

        long id = ServiceTariffDAOImpl.getInstance().checkTariffIsExist(tariff);
        if (id > 0) {
            tariff.setId(id);
            ServiceTariffDAOImpl.getInstance().editTariff(tariff);
        } else {
            LOG.debug("ADD");
            ServiceTariffDAOImpl.getInstance().addNewTariff(tariff);
        }

    }

    public static void removeTariff(String tariffName) {
        ServiceTariffDAOImpl.getInstance().removeTariff(tariffName);
    }

    public static Tariff getTariffById(long tariffId) {
        return ServiceTariffDAOImpl.getInstance().getTariffById(tariffId);
    }

    public static int getServiceCount() {
        return ServiceTariffDAOImpl.getInstance().getServiceCount();
    }

    public static int getTariffCount() {
        return ServiceTariffDAOImpl.getInstance().getTariffCount();
    }

    public static void updateTariffAccountService(AccountService linkedService, long tariffId) {
        if (!linkedService.isPayed()) {
            linkedService.setTariffId(tariffId);
            AccountDAOImpl.getInstance().updateServiceToAccount(linkedService);
            return;
        }

        Date today = Date.valueOf(LocalDate.now());
        Date oldNextPaymentDay = linkedService.getNexPaymentDay();
        Date nextPaymentDay = Date.valueOf(LocalDate.now().plusMonths(1));

        int differenceDays = (int) ChronoUnit.DAYS.between(today.toLocalDate(), oldNextPaymentDay.toLocalDate());
        int oldTariffPrice = ServiceTariffDataManager.getTariffById(linkedService.getTariffId()).getPrice();
        int newTariffPrice = ServiceTariffDataManager.getTariffById(tariffId).getPrice() ;

        int diffPrice = (int) (newTariffPrice - (differenceDays * ((double)oldTariffPrice / DAY_IN_MONTH)));

        int paymentForUpdatedTariff = Math.max(diffPrice, 0);

        linkedService.setTariffId(tariffId);
        linkedService.setActivationTime(today);
        linkedService.setNexPaymentDay(nextPaymentDay);
        linkedService.setStatus(false);
        linkedService.setPayed(false);
        linkedService.setPaymentAmount(paymentForUpdatedTariff);

        AccountDAOImpl.getInstance().updateServiceToAccount(linkedService);
    }

    public static void updateAccountService(AccountService accountService) {
        AccountDAOImpl.getInstance().updateServiceToAccount(accountService);
    }

    public static int getActiveAccountServiceCount(long accountId) {
        return ServiceTariffDAOImpl.getInstance().getActiveAccountService(accountId);
    }

}
