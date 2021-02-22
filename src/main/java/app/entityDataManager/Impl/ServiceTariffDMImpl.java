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
        ServiceTariffDAOImpl serviceTariffDAO = DAOFactoryImpl.getInstance().getServiceTariffDAO();
        return serviceTariffDAO.getServiceById(id);
    }

    public List<Service> getAllServices() {
        ServiceTariffDAOImpl serviceTariffDAO = DAOFactoryImpl.getInstance().getServiceTariffDAO();
        return serviceTariffDAO.getAllServices();
    }

    public List<Service> getAllServicesWithoutTariffs() {
        ServiceTariffDAOImpl serviceTariffDAO = DAOFactoryImpl.getInstance().getServiceTariffDAO();
        return serviceTariffDAO.getAllServicesWithoutTariffs();
    }

    public List<Tariff> getCertainServiceTariffs(long service_id, int page, int pageSize, String orderBy, String desc) {
        int tariffsToGet = (page - 1) * pageSize;
        ServiceTariffDAOImpl serviceTariffDM = DAOFactoryImpl.getInstance().getServiceTariffDAO();
        return serviceTariffDM.getPartTariffsByServiceId(service_id, pageSize, tariffsToGet, orderBy, desc);
    }

    public AccountService getAccountService(long accountId, long serviceId) {
        return DAOFactoryImpl.getInstance().getServiceTariffDAO().getAccountServiceByAccountId(accountId, serviceId);
    }

    public List<AccountService> getAllAccountServices(long accountId) {
        ServiceTariffDAOImpl serviceTariffDM = DAOFactoryImpl.getInstance().getServiceTariffDAO();
        return serviceTariffDM.getAllAccountServicesByAccountId(accountId);
    }

    public int getServiceTariffCount(long serviceId) {
        ServiceTariffDAOImpl serviceTariffDM = DAOFactoryImpl.getInstance().getServiceTariffDAO();
        return serviceTariffDM.getServiceTariffCount(serviceId);
    }

    //Tariff actions
    public void applyTariff(int serviceId, String tariffName, String tariffDescription, int tariffPrice) {
        Tariff tariff = new Tariff();

        tariff.setServiceId(serviceId);
        tariff.setName(tariffName);
        tariff.setDescription(tariffDescription);
        tariff.setPrice(tariffPrice);

        LOG.debug(tariff.toString());
        ServiceTariffDAOImpl serviceTariffDM = DAOFactoryImpl.getInstance().getServiceTariffDAO();

        long id = serviceTariffDM.checkTariffIsExist(tariff);
        if (id > 0) {
            tariff.setId(id);
            serviceTariffDM.editTariff(tariff);
        } else {
            LOG.debug("ADD");
            serviceTariffDM.addNewTariff(tariff);
        }

    }

    public void removeTariff(String tariffName) {
        ServiceTariffDAOImpl serviceTariffDM = DAOFactoryImpl.getInstance().getServiceTariffDAO();
        serviceTariffDM.removeTariff(tariffName);
    }

    public Tariff getTariffById(long tariffId) {
        ServiceTariffDAOImpl serviceTariffDM = DAOFactoryImpl.getInstance().getServiceTariffDAO();
        return serviceTariffDM.getTariffById(tariffId);
    }

    public int getServiceCount() {
        ServiceTariffDAOImpl serviceTariffDM = DAOFactoryImpl.getInstance().getServiceTariffDAO();
        return serviceTariffDM.getServiceCount();
    }

    public int getTariffCount() {
        ServiceTariffDAOImpl serviceTariffDM = DAOFactoryImpl.getInstance().getServiceTariffDAO();
        return serviceTariffDM.getTariffCount();
    }

    public void updateTariffAccountService(AccountService linkedService, long tariffId) {
        AccountDAOImpl accountDAO = DAOFactoryImpl.getInstance().getAccountDAO();
        if (!linkedService.isPayed()) {
            linkedService.setTariffId(tariffId);
            accountDAO.updateServiceToAccount(linkedService);
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

        accountDAO.updateServiceToAccount(linkedService);
    }

    public void updateAccountService(AccountService accountService) {
        AccountDAOImpl accountDAO = DAOFactoryImpl.getInstance().getAccountDAO();
        accountDAO.updateServiceToAccount(accountService);
    }

    public int getActiveAccountServiceCount(long accountId) {
        ServiceTariffDAOImpl serviceTariffDAO = DAOFactoryImpl.getInstance().getServiceTariffDAO();
        return serviceTariffDAO.getActiveAccountServiceCount(accountId);
    }

}
