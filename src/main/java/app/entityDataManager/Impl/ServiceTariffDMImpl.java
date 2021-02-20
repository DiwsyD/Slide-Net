package app.entityDataManager.Impl;

import app.dao.Impl.AccountDAOImpl;
import app.dao.Impl.ServiceTariffDAOImpl;
import app.entity.AccountService;
import app.entity.Service;
import app.entity.Tariff;
import app.entityDataManager.ServiceTariffDM;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ServiceTariffDMImpl implements ServiceTariffDM {
    private static final Logger LOG = Logger.getLogger(ServiceTariffDMImpl.class);

    public static final int DAY_IN_MONTH = 30;

    public Service getServiceById(long id) {
        return ServiceTariffDAOImpl.getInstance().getServiceById(id);
    }

    public List<Service> getAllServices() {
        return ServiceTariffDAOImpl.getInstance().getAllServices();
    }

    public List<Service> getAllServicesWithoutTariffs() {
        return ServiceTariffDAOImpl.getInstance().getAllServicesWithoutTariffs();
    }

    public List<Tariff> getCertainServiceTariffs(long service_id, int page, int pageSize, String orderBy, String desc) {
        int tariffsToGet = (page - 1) * pageSize;
        return ServiceTariffDAOImpl.getInstance().getPartTariffsByServiceId(service_id, pageSize, tariffsToGet, orderBy, desc);
    }

    public AccountService getAccountService(long accountId, long serviceId) {
        return ServiceTariffDAOImpl.getInstance().getAccountServiceByAccountId(accountId, serviceId);
    }

    public List<AccountService> getAllAccountServices(long accountId) {
        return ServiceTariffDAOImpl.getInstance().getAccountServicesByAccountId(accountId);
    }

    public int getServiceTariffCount(long serviceId) {
        return ServiceTariffDAOImpl.getInstance().getServiceTariffCount(serviceId);
    }

    //Tariff actions
    public void applyTariff(int serviceId, String tariffName, String tariffDescription, int tariffPrice) {
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

    public void removeTariff(String tariffName) {
        ServiceTariffDAOImpl.getInstance().removeTariff(tariffName);
    }

    public Tariff getTariffById(long tariffId) {
        return ServiceTariffDAOImpl.getInstance().getTariffById(tariffId);
    }

    public int getServiceCount() {
        return ServiceTariffDAOImpl.getInstance().getServiceCount();
    }

    public int getTariffCount() {
        return ServiceTariffDAOImpl.getInstance().getTariffCount();
    }

    public void updateTariffAccountService(AccountService linkedService, long tariffId) {
        if (!linkedService.isPayed()) {
            linkedService.setTariffId(tariffId);
            AccountDAOImpl.getInstance().updateServiceToAccount(linkedService);
            return;
        }

        Date today = Date.valueOf(LocalDate.now());
        Date oldNextPaymentDay = linkedService.getNexPaymentDay();
        Date nextPaymentDay = Date.valueOf(LocalDate.now().plusMonths(1));

        int differenceDays = (int) ChronoUnit.DAYS.between(today.toLocalDate(), oldNextPaymentDay.toLocalDate());
        int oldTariffPrice = getTariffById(linkedService.getTariffId()).getPrice();
        int newTariffPrice = getTariffById(tariffId).getPrice() ;

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

    public void updateAccountService(AccountService accountService) {
        AccountDAOImpl.getInstance().updateServiceToAccount(accountService);
    }

    public int getActiveAccountServiceCount(long accountId) {
        return ServiceTariffDAOImpl.getInstance().getActiveAccountService(accountId);
    }

}
