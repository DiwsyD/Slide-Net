package app.model;

import app.database.dao.AccountDAO;
import app.database.dao.ServiceTariffDAO;
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

    public static AccountService getAccountService(long accountId, long serviceId) {
        return ServiceTariffDAO.getInstance().getAccountServiceByAccountId(accountId, serviceId);
    }

    public static List<AccountService> getAllAccountServices(long accountId) {
        return ServiceTariffDAO.getInstance().getAccountServicesByAccountId(accountId);
    }

    public static int getServiceTariffCount(long serviceId) {
        return ServiceTariffDAO.getInstance().getServiceTariffCount(serviceId);
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

    public static Tariff getTariffById(long tariffId) {
        return ServiceTariffDAO.getInstance().getTariffById(tariffId);
    }

    public static int getServiceCount() {
        return ServiceTariffDAO.getInstance().getServiceCount();
    }

    public static int getTariffCount() {
        return ServiceTariffDAO.getInstance().getTariffCount();
    }

    public static void updateTariffAccountService(AccountService linkedService, long tariffId) {
        if (!linkedService.isPayed()) {
            linkedService.setTariffId(tariffId);
            AccountDAO.getInstance().updateServiceToAccount(linkedService);
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

        AccountDAO.getInstance().updateServiceToAccount(linkedService);
    }

    public static void updateAccountService(AccountService accountService) {
        AccountDAO.getInstance().updateServiceToAccount(accountService);
    }

    public static int getActiveAccountServiceCount(long accountId) {
        return ServiceTariffDAO.getInstance().getActiveAccountService(accountId);
    }

}
