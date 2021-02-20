package app.entityDataManager.Impl;

import app.dao.Impl.AccountDAOImpl;
import app.dao.Impl.ServiceTariffDAOImpl;
import app.entity.AccountService;
import app.entity.Service;
import app.entity.Tariff;
import app.entityDataManager.ServiceTariffDM;
import app.factory.Impl.DAOFactoryImpl;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ServiceTariffDMImpl implements ServiceTariffDM {
    private static final Logger LOG = Logger.getLogger(ServiceTariffDMImpl.class);

    public static final int DAY_IN_MONTH = 30;

    public Service getServiceById(long id) {
        return DAOFactoryImpl.getInstance().getServiceTariffDAO().getServiceById(id);
    }

    public List<Service> getAllServices() {
        return DAOFactoryImpl.getInstance().getServiceTariffDAO().getAllServices();
    }

    public List<Service> getAllServicesWithoutTariffs() {
        return DAOFactoryImpl.getInstance().getServiceTariffDAO().getAllServicesWithoutTariffs();
    }

    public List<Tariff> getCertainServiceTariffs(long service_id, int page, int pageSize, String orderBy, String desc) {
        int tariffsToGet = (page - 1) * pageSize;
        return DAOFactoryImpl.getInstance().getServiceTariffDAO().getPartTariffsByServiceId(service_id, pageSize, tariffsToGet, orderBy, desc);
    }

    public AccountService getAccountService(long accountId, long serviceId) {
        return DAOFactoryImpl.getInstance().getServiceTariffDAO().getAccountServiceByAccountId(accountId, serviceId);
    }

    public List<AccountService> getAllAccountServices(long accountId) {
        return DAOFactoryImpl.getInstance().getServiceTariffDAO().getAccountServicesByAccountId(accountId);
    }

    public int getServiceTariffCount(long serviceId) {
        return DAOFactoryImpl.getInstance().getServiceTariffDAO().getServiceTariffCount(serviceId);
    }

    //Tariff actions
    public void applyTariff(int serviceId, String tariffName, String tariffDescription, int tariffPrice) {
        Tariff tariff = new Tariff();

        tariff.setServiceId(serviceId);
        tariff.setName(tariffName);
        tariff.setDescription(tariffDescription);
        tariff.setPrice(tariffPrice);

        LOG.debug(tariff.toString());

        long id = DAOFactoryImpl.getInstance().getServiceTariffDAO().checkTariffIsExist(tariff);
        if (id > 0) {
            tariff.setId(id);
            DAOFactoryImpl.getInstance().getServiceTariffDAO().editTariff(tariff);
        } else {
            LOG.debug("ADD");
            DAOFactoryImpl.getInstance().getServiceTariffDAO().addNewTariff(tariff);
        }

    }

    public void removeTariff(String tariffName) {
        DAOFactoryImpl.getInstance().getServiceTariffDAO().removeTariff(tariffName);
    }

    public Tariff getTariffById(long tariffId) {
        return DAOFactoryImpl.getInstance().getServiceTariffDAO().getTariffById(tariffId);
    }

    public int getServiceCount() {
        return DAOFactoryImpl.getInstance().getServiceTariffDAO().getServiceCount();
    }

    public int getTariffCount() {
        return DAOFactoryImpl.getInstance().getServiceTariffDAO().getTariffCount();
    }

    public void updateTariffAccountService(AccountService linkedService, long tariffId) {
        if (!linkedService.isPayed()) {
            linkedService.setTariffId(tariffId);
            DAOFactoryImpl.getInstance().getAccountDAO().updateServiceToAccount(linkedService);
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

        DAOFactoryImpl.getInstance().getAccountDAO().updateServiceToAccount(linkedService);
    }

    public void updateAccountService(AccountService accountService) {
        DAOFactoryImpl.getInstance().getAccountDAO().updateServiceToAccount(accountService);
    }

    public int getActiveAccountServiceCount(long accountId) {
        return DAOFactoryImpl.getInstance().getServiceTariffDAO().getActiveAccountService(accountId);
    }

}
